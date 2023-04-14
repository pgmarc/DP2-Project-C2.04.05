
package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		final int courseId = super.getRequest().getData("id", int.class);
		final int accountId = super.getRequest().getPrincipal().getAccountId();
		final int studentId = this.repository.findOneStudentByUserAccountId(accountId).getId();
		final Enrolment enrolment = this.repository.findOneEnrolmentByCourseIdAndStudentId(courseId, studentId);

		tuple = super.unbind(object, "code", "title", "courseAbstract", "nature", "retailPrice", "moreInfo", "lecturer", "draftMode");
		tuple.put("enrolment", enrolment == null ? null : enrolment.getId());

		super.getResponse().setData(tuple);
	}
}
