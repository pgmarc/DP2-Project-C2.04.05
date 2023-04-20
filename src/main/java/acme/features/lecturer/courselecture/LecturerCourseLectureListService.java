
package acme.features.lecturer.courselecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.CourseLecture;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureListService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	protected LecturerLectureRepository			lectureRepository;

	@Autowired
	protected LecturerCourseRepository			courseRepository;

	// AbstractService<Authenticated, Consumer> ---------------------------


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
		final Principal principal = super.getRequest().getPrincipal();
		final List<CourseLecture> object = this.repository.getCourseLecturesFromLecturer(principal.getAccountId());

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
