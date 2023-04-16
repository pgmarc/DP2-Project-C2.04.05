
package acme.features.assistant.tutorialsession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		status = super.getRequest().getPrincipal().isAuthenticated();

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
		assert object != null;

		Tuple tuple;
		int tutorialId;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tuple = super.unbind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));

		super.getResponse().setData(tuple);
	}
}
