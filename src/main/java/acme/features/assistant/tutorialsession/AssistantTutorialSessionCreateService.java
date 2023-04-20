/*
 * EmployerJobCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialsession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("tutorialId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));

		object = new TutorialSession();
		object.setTutorial(tutorial);
		object.setTitle("");
		object.setAbstrac("");
		object.setGoals("");
		object.setStartDate(new Date());
		object.setFinishDate(new Date());
		object.setSessionNature(Nature.THEORETICAL);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
	}

	@Override
	public void validate(final TutorialSession object) {
		if (!super.getBuffer().getErrors().hasErrors("tutorial")) {
			Tutorial tutorial;

			tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));

			super.state(tutorial.isDraftMode(), "assistant", "assistant.tutorialsession.form.error.tutorial");
		}
		if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
			final Date start = object.getStartDate();
			final Date finish = object.getFinishDate();
			boolean state;

			state = MomentHelper.isBefore(start, finish);
			state = state && MomentHelper.isLongEnough(start, finish, 1, ChronoUnit.HOURS);
			state = state && !MomentHelper.isLongEnough(start, finish, 5, ChronoUnit.HOURS);

			super.state(state, "finishDate", "assistant.tutorialsession.form.error.finishDate");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			final Date start = object.getStartDate();

			super.state(MomentHelper.isLongEnough(MomentHelper.getCurrentMoment(), start, 1, ChronoUnit.DAYS), "startDate", "assistant.tutorialsession.form.error.startDate");
		}
		if (!super.getBuffer().getErrors().hasErrors("sessionNature")) {
			Nature nature;

			nature = object.getSessionNature();
			super.state(nature != null, "sessionNature", "assistant.tutorialsession.form.error.nature");
		}
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));
		tutorial = this.getUpdatedTutorial(tutorial, object);

		this.repository.save(object);
		this.repository.save(tutorial);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;
		SelectChoices choices;

		tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));
		choices = SelectChoices.from(Nature.class, object.getSessionNature());

		tuple = super.unbind(object, "title", "abstrac", "goals", "startDate", "finishDate");
		tuple.put("natures", choices);
		tuple.put("sessionNature", choices.getSelected().getKey());
		tuple.put("tutorialId", tutorial.getId());
		tuple.put("draftMode", tutorial.isDraftMode());

		super.getResponse().setData(tuple);
	}

	private Tutorial getUpdatedTutorial(final Tutorial tutorial, final TutorialSession object) {
		double newHours;
		Date start;
		Date finish;

		start = object.getStartDate();
		finish = object.getFinishDate();
		newHours = (double) MomentHelper.computeDuration(start, finish).toMinutes() / 60;

		tutorial.setEstimatedHours(tutorial.getEstimatedHours() + newHours);

		return tutorial;
	}

}
