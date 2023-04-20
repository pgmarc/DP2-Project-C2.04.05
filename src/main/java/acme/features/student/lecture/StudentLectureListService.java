
package acme.features.student.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

	@Autowired
	protected StudentLectureRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		List<Lecture> lectures;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		lectures = this.repository.findAllLecturesByCourseId(courseId);

		super.getBuffer().setData(lectures);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "nature", "body", "moreInfo");
		tuple.put("lecturer", object.getLecturer().getIdentity().getFullName());

		super.getResponse().setData(tuple);
	}
}
