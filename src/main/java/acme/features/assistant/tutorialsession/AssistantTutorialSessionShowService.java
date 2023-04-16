
package acme.features.assistant.tutorialsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionShowService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final TutorialSession session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTutorialSessionsById(id);

		super.getBuffer().setData(session);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;
		SelectChoices choices;

		tutorial = object.getTutorial();
		choices = SelectChoices.from(Nature.class, object.getSessionNature());

		tuple = super.unbind(object, "title", "abstrac", "goals", "startDate", "finishDate");
		tuple.put("natures", choices);
		tuple.put("sessionNature", choices.getSelected().getKey());
		tuple.put("tutorialId", tutorial.getId());
		tuple.put("draftMode", tutorial.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
