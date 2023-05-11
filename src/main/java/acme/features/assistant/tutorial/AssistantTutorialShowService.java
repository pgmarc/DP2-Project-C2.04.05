
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
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
		Tutorial tutorial;
		Assistant assistant;
		int id;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(id);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = super.getRequest().getPrincipal().hasRole(assistant) && assistant.getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();

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
		Assistant assistant;
		Course course;
		Tuple tuple;

		assistant = object.getAssistant();
		course = object.getCourse();

		tuple = super.unbind(object, "code", "title", "abstrac", "goals", "estimatedHours", "draftMode");
		tuple.put("assistantName", assistant.getIdentity().getFullName());
		tuple.put("courseId", course.getId());

		super.getResponse().setData(tuple);
	}
}
