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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
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

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));

		super.bind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		object.setTutorial(tutorial);
	}

	@Override
	public void validate(final TutorialSession object) {
		if (!super.getBuffer().getErrors().hasErrors("tutorial")) {
			Tutorial tutorial;

			tutorial = this.repository.findOneTutorialById(super.getRequest().getData("tutorialId", int.class));

			super.state(tutorial.isDraftMode(), "assistant", "assistant.tutorialsession.form.error.tutorial");
		}
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;

		tutorial = object.getTutorial();

		tuple = super.unbind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		tuple.put("tutorialId", tutorial.getId());
		tuple.put("draftMode", tutorial.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
