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

package acme.features.lecturer.courselecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.CourseLecture;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureShowService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	protected LecturerLectureRepository			lectureRepository;

	@Autowired
	protected LecturerCourseRepository			courseRepository;


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
		CourseLecture courseLecture;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		courseLecture = this.repository.getCourseLectureById(masterId);
		lecturer = courseLecture == null ? null : courseLecture.getCourse().getLecturer();
		status = courseLecture != null && courseLecture.getCourse().isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		final CourseLecture object;
		id = super.getRequest().getData("id", int.class);

		object = this.repository.getCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		Tuple tuple;
		tuple = super.unbind(object, "id");
		tuple.put("course", object.getCourse().getTitle());
		tuple.put("lecture", object.getLecture().getTitle());
		super.getResponse().setData(tuple);
	}

}
