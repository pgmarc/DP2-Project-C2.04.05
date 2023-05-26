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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
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
		Tutorial object;
		Course defaultCourse;
		Assistant assistant;

		defaultCourse = ((List<Course>) this.repository.findAllCourses()).get(0);
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new Tutorial();
		object.setDraftMode(true);
		object.setAssistant(assistant);
		object.setCourse(defaultCourse);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		Course course;
		Assistant assistant;

		course = object.getCourse();
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());

		super.bind(object, "code", "title", "abstrac", "goals", "estimatedHours");
		object.setDraftMode(true);
		object.setAssistant(assistant);
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {

		if (!super.getBuffer().getErrors().hasErrors("assistant")) {
			Assistant assistant;

			assistant = object.getAssistant();
			super.state(assistant.getId() == super.getRequest().getPrincipal().getActiveRoleId(), "assistant", "assistant.tutorial.form.error.assistant");
		}
		if (!super.getBuffer().getErrors().hasErrors("course")) {
			Course course;

			course = object.getCourse();
			super.state(course != null, "course", "assistant.tutorial.form.error.course");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		final TutorialSession session;

		session = this.createDefaultSession(object);
		object.setEstimatedHours(1.);

		this.repository.save(object);
		this.repository.save(session);
	}

	@Override
	public void unbind(final Tutorial object) {
		Assistant assistant;
		Course course;
		Collection<Course> courses;
		Tuple tuple;
		SelectChoices choices;

		assistant = object.getAssistant();
		course = object.getCourse();
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", course);

		tuple = super.unbind(object, "code", "title", "abstrac", "goals", "estimatedHours", "draftMode");
		tuple.put("assistant", assistant);
		tuple.put("course", this.repository.findOneCourseByCode(choices.getSelected().getKey()));
		tuple.put("courses", choices);
		tuple.put("assistantName", assistant.getIdentity().getFullName());
		tuple.put("courseId", course.getId());

		super.getResponse().setData(tuple);
	}

	private TutorialSession createDefaultSession(final Tutorial tutorial) {
		TutorialSession session;
		SimpleDateFormat dateFormatter;
		Date start = new Date();
		Date finish = new Date();

		dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			start = dateFormatter.parse("2023/07/30 22:00");
			finish = dateFormatter.parse("2023/07/30 23:00");
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		session = new TutorialSession();
		session.setTitle("Default session");
		session.setAbstrac("This is a default session");
		session.setGoals("Update this session");
		session.setSessionNature(Nature.THEORETICAL);
		session.setStartDate(start);
		session.setFinishDate(finish);
		session.setTutorial(tutorial);

		return session;
	}

}
