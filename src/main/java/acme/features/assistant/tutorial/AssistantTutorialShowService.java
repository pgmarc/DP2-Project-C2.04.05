
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		int assistantId;
		int id;

		id = super.getRequest().getData("id", int.class);
		assistantId = this.repository.findOneTutorialById(id).getAssistant().getId();
		status = super.getRequest().getPrincipal().hasRole(Assistant.class) && assistantId == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Tutorial tutorial;
		int id;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstrac", "goals", "estimatedHours");
		tuple.put("assistant", object.getAssistant());
		tuple.put("course", object.getCourse());

		super.getResponse().setData(tuple);
	}
}
