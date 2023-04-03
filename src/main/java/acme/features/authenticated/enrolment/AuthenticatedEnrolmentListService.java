
package acme.features.authenticated.enrolment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Consumer;
import acme.roles.Student;

@Service
public class AuthenticatedEnrolmentListService extends AbstractService<Authenticated, Enrolment> {

	@Autowired
	protected AuthenticatedEnrolmentRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRole(Consumer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		List<Enrolment> enrolments;
		Principal principal;
		int userAccountId;
		Student student;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		student = this.repository.findOneStudentByUserAccountId(userAccountId);
		enrolments = this.repository.findAllEnrolmentsByStudentId(student.getId());

		super.getBuffer().setData(enrolments);
	}

	@Override
	public void unbind(final Enrolment object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "student", "course");

		super.getResponse().setData(tuple);
	}
}
