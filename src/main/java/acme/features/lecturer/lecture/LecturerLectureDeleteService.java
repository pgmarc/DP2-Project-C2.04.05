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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.course.Lecture;
import acme.enumerates.Nature;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected LecturerCourseRepository	courseRepository;


	private void updateCourseNature(final Course object) {
		Stream<Nature> lectures;
		lectures = this.repository.getLecturesFromCourse(object.getId()).stream().map(Lecture::getNature);
		final int theoryLectures = lectures.filter(n -> n == Nature.THEORETICAL).collect(Collectors.toList()).size();
		final int handsOnLectures = lectures.filter(n -> n == Nature.HANDS_ON).collect(Collectors.toList()).size();
		final int balancedLectures = lectures.filter(n -> n == Nature.BALANCED).collect(Collectors.toList()).size();
		if (theoryLectures > handsOnLectures && theoryLectures > balancedLectures)
			object.setNature(Nature.THEORETICAL);
		else if (handsOnLectures > theoryLectures && handsOnLectures > balancedLectures)
			object.setNature(Nature.HANDS_ON);
		else
			object.setNature(Nature.BALANCED);
		this.courseRepository.save(object);
	}

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
		Lecture lecture;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		lecture = this.repository.getLectureById(masterId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = lecture != null && lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.getLectureById(id);

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
		int lectureId;
		final List<CourseLecture> relations;
		lectureId = super.getRequest().getData("id", int.class);
		relations = this.repository.getRelationsFromLectureId(lectureId);
		final List<Course> courses;
		courses = this.courseRepository.getCoursesFromLecture(object.getId());

		this.repository.deleteAll(relations);
		this.repository.delete(object);
		courses.stream().forEach(this::updateCourseNature);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "title", "lectureAbstract", "nature", "body", "moreInfo");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}

}
