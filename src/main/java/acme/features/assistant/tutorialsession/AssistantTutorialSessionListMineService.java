
package acme.features.assistant.tutorialsession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListMineService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("tutorialId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated() && super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<TutorialSession> sessions;
		int tutorialId;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		sessions = this.repository.findTutorialSessionsByTutorialId(tutorialId);

		super.getBuffer().setData(sessions);
	}

	@Override
	public void unbind(final TutorialSession object) {
		Tuple tuple;
		int tutorialId;
		Tutorial tutorial;
		boolean showCreate;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		tuple = super.unbind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		super.getResponse().setGlobal("tId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);

		super.getResponse().setData(tuple);
	}
}
