
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Activity activity;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		activity = this.repository.findOneActivityById(masterId);
		student = activity == null ? null : activity.getEnrolment().getStudent();
		status = activity != null && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.updateWorkTime(object);

		this.repository.delete(object);
	}

	private void updateWorkTime(final Activity object) {
		Enrolment enrolment;
		int enrolmentId;
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (double) (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) / 60;
		enrolmentId = object.getEnrolment().getId();
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		final double newWorkTime = Math.round((enrolment.getWorkTime() - horas - minutosEnPorcentaje) * 100.0) / 100.0;
		enrolment.setWorkTime(newWorkTime);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
		super.getResponse().setData(tuple);
	}
}
