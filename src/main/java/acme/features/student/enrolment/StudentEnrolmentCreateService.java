
package acme.features.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			super.getResponse().setChecked(true);
		else {
			boolean status;

			status = super.getRequest().hasData("courseId", int.class);

			super.getResponse().setChecked(status);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		Student student;
		Course course;
		Principal principal;
		int userAccountId;
		int courseId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		courseId = super.getRequest().getData("courseId", int.class);

		student = this.repository.findOneStudentByUserAccountId(userAccountId);
		course = this.repository.findOneCourseById(courseId);

		object = new Enrolment();
		object.setStudent(student);
		object.setDraftMode(true);
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "workTime");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
