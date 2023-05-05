/*
 * EmployerJobUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.course;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.getCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.getCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		super.bind(object, "code", "title", "courseAbstract", "retailPrice", "moreInfo");

	}

	@Override
	public void validate(final Course object) {
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final List<Course> courses = this.repository.getAllCourses();
			super.state(courses.stream().map(Course::getCode).noneMatch(c -> Objects.equals(c, object.getCode())), "code", "lecturer.course.form.error.duplicatedCode");
			if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
				final String currencies = this.repository.getSupportedCurrencies();
				super.state(currencies.contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.course.form.error.invalidCurrency");
			}
			if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
				super.state(object.getRetailPrice().getAmount() >= 0, "retailPrice", "lecturer.course.form.error.negativePrice");
		}
	}

	@Override
	public void perform(final Course object) {
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "code", "title", "courseAbstract", "nature", "retailPrice", "moreInfo");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}

}
