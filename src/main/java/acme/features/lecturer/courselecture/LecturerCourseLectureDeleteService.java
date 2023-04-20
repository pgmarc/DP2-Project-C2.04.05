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
import acme.enumerates.Nature;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureDeleteService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	protected LecturerLectureRepository			lectureRepository;

	@Autowired
	protected LecturerCourseRepository			courseRepository;


	private void updateCourseNature(final Course object) {
		List<Lecture> lectures;
		lectures = this.lectureRepository.getLecturesFromCourse(object.getId());
		if (lectures.stream().map(Lecture::getNature).allMatch(n -> n == Nature.THEORETICAL))
			object.setNature(Nature.THEORETICAL);
		else if (lectures.stream().map(Lecture::getNature).allMatch(n -> n == Nature.HANDS_ON))
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
		CourseLecture courseLecture;
		Lecturer lecturer;
		Course course;
		masterId = super.getRequest().getData("id", int.class);
		courseLecture = this.repository.getCourseLectureById(masterId);
		course = courseLecture.getCourse();

		lecturer = courseLecture == null ? null : courseLecture.getCourse().getLecturer();
		status = courseLecture != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.getCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		super.bind(object, "code", "title", "courseAbstract", "retailPrice", "moreInfo");
	}

	@Override
	public void validate(final CourseLecture object) {
		//No custom constraints to implement
	}

	@Override
	public void perform(final CourseLecture object) {
		final Course course = object.getCourse();
		this.repository.delete(object);
		this.updateCourseNature(course);
	}

	@Override
	public void unbind(final CourseLecture object) {
		Tuple tuple;
		int lecturerId;
		final SelectChoices lectureChoices;
		final SelectChoices courseChoices;
		lecturerId = super.getRequest().getPrincipal().getAccountId();
		final List<Lecture> lectures = this.lectureRepository.getAllLecturesFromLecturer(lecturerId);
		final List<Course> courses = this.courseRepository.getCoursesFromLecturer(lecturerId);
		courseChoices = SelectChoices.from(courses, "title", object.getCourse());
		lectureChoices = SelectChoices.from(lectures, "title", object.getLecture());
		tuple = new Tuple();
		tuple.put("course", courseChoices.getSelected());
		tuple.put("courses", courseChoices);
		tuple.put("lecture", lectureChoices.getSelected());
		tuple.put("lectures", lectureChoices);
		super.getResponse().setData(tuple);
	}

}
