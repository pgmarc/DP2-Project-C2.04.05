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

package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

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
		Tutorial object;
		Course course;
		Assistant assistant;

		course = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class));
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new Tutorial();
		object.setDraftMode(true);
		object.setAssistant(assistant);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		Course course;
		Assistant assistant;

		course = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class));
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "code", "title", "abstrac", "goals", "estimatedHours");
		object.setDraftMode(true);
		object.setAssistant(assistant);
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("assistant")) {
			Assistant assistant;

			assistant = object.getAssistant();
			super.state(assistant.getId() == super.getRequest().getPrincipal().getActiveRoleId(), "assistant", "assistant.tutorial.form.error.assistant");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
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
