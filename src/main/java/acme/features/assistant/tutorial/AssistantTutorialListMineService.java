
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListMineService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Tutorial> tutorials;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		tutorials = this.repository.findTutorialsByAssistantId(id);

		super.getBuffer().setData(tutorials);
	}

	@Override
	public void unbind(final Tutorial object) {

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstrac", "goals", "estimatedHours", "draftMode");
		tuple.put("assistant", object.getAssistant());
		tuple.put("course", object.getCourse());

		super.getResponse().setData(tuple);
	}
}
