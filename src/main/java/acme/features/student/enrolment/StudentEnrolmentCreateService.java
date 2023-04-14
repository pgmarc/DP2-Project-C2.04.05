
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
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Enrolment enrolment;
		int courseId;
		int studentId;
		int accountId;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		if (status) {
			courseId = super.getRequest().getData("courseId", int.class);
			accountId = super.getRequest().getPrincipal().getAccountId();
			studentId = this.repository.findOneStudentByUserAccountId(accountId).getId();
			enrolment = this.repository.findOneEnrolmentByCourseIdAndStudentId(courseId, studentId);

			status = enrolment == null;
		}

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
		final Enrolment EnrolmentWithSameCode = this.repository.findOneEnrolmentByCode(object.getCode());

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(EnrolmentWithSameCode == null || EnrolmentWithSameCode.equals(object), "code", "authenticated.activity.form.validate.code");
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
