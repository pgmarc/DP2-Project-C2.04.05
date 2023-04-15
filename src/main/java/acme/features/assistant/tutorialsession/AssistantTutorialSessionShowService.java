
package acme.features.assistant.tutorialsession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
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
		Tuple tuple;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialByTutorialSessionId(super.getRequest().getData("id", int.class));

		tuple = super.unbind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		tuple.put("tutorialId", tutorial);
		tuple.put("draftMode", tutorial.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
