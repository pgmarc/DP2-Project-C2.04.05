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

package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final Lecture object;
		final int id = super.getRequest().getPrincipal().getAccountId();
		final Lecturer lecturer = this.repository.getLecturerByAccountId(id);

		object = new Lecture();
		object.setLecturer(lecturer);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		super.bind(object, "title", "lectureAbstract", "nature", "body", "moreInfo");
	}

	@Override
	public void validate(final Lecture object) {
		//No custom constraints to implement
	}

	@Override
	public void perform(final Lecture object) {
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "title", "lectureAbstract", "nature", "body", "moreInfo", "draftMode");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}

}
