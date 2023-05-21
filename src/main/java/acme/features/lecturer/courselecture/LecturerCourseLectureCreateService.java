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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.course.Lecture;
import acme.features.lecturer.UpdateCourseNature;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreateService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	protected LecturerLectureRepository			lectureRepository;

	@Autowired
	protected LecturerCourseRepository			courseRepository;


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
		final CourseLecture object;
		object = new CourseLecture();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		Course course;
		Lecture lecture;
		int courseId;
		int lectureId;
		super.bind(object, "id");
		courseId = super.getRequest().getData("course", int.class);
		lectureId = super.getRequest().getData("lecture", int.class);
		course = this.courseRepository.getCourseById(courseId);
		lecture = this.lectureRepository.getLectureById(lectureId);
		object.setCourse(course);
		object.setLecture(lecture);

	}

	@Override
	public void validate(final CourseLecture object) {
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(object.getCourse().isDraftMode(), "course", "lecturer.courseLecture.form.error.publishedCourse");
		if (!super.getBuffer().getErrors().hasErrors("course") && !super.getBuffer().getErrors().hasErrors("lecture"))
			super.state(object.getCourse().getLecturer().getId() == object.getLecture().getLecturer().getId(), "course", "lecturer.course.form.error.notYourCourse");
		if (!super.getBuffer().getErrors().hasErrors("course") && !super.getBuffer().getErrors().hasErrors("lecture")) {
			final int courseId = object.getCourse().getId();
			final int lectureId = object.getLecture().getId();
			final List<CourseLecture> cl = this.repository.getCourseLecturesByIds(courseId, lectureId);
			super.state(cl.isEmpty(), "lecture", "lecturer.course.form.error.lectureInCourse");
		}

	}

	@Override
	public void perform(final CourseLecture object) {
		int id;
		Course course;
		id = object.getCourse().getId();
		course = this.courseRepository.getCourseById(id);
		this.repository.save(object);
		UpdateCourseNature.updateCourseNature(course, this.lectureRepository, this.courseRepository);
	}

	@Override
	public void unbind(final CourseLecture object) {
		Tuple tuple;
		int lecturerId;
		SelectChoices lectureChoices;
		final SelectChoices courseChoices;
		lecturerId = super.getRequest().getPrincipal().getAccountId();
		final List<Lecture> lectures = this.lectureRepository.getAllLecturesFromLecturer(lecturerId);
		final List<Course> courses = this.courseRepository.getCoursesFromLecturer(lecturerId);
		courseChoices = SelectChoices.from(courses, "title", object.getCourse());
		lectureChoices = SelectChoices.from(lectures, "title", object.getLecture());
		tuple = super.unbind(object, "id");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("courses", courseChoices);
		tuple.put("lecture", lectureChoices.getSelected().getKey());
		tuple.put("lectures", lectureChoices);
		super.getResponse().setData(tuple);
	}

}
