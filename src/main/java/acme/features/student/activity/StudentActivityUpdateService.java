
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.enumerates.Nature;
import acme.features.student.enrolment.StudentEnrolmentRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository		repository;

	@Autowired
	protected StudentEnrolmentRepository	enrolmentRepository;

	// AbstractService interface ----------------------------------------------ç

	protected double						workTimeAnterior;


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

		this.workTimeAnterior = this.getActivityWorkTime(object);

		super.getBuffer().setData(object);
	}

	private double getActivityWorkTime(final Activity object) {
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (double) (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) / 60;
		return Math.round((horas + minutosEnPorcentaje) * 100.0) / 100.0;
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "*", "Fecha final debe estar después de fecha inicial");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;
		final Enrolment enrolment = this.updateWorkTime(object);

		this.repository.save(object);
		this.enrolmentRepository.save(enrolment);
	}

	private Enrolment updateWorkTime(final Activity object) {
		Enrolment enrolment;
		int enrolmentId;
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (double) (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) / 60;
		enrolmentId = object.getEnrolment().getId();
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		final double newWorkTime = Math.round((enrolment.getWorkTime() - this.workTimeAnterior + horas + minutosEnPorcentaje) * 100.0) / 100.0;
		enrolment.setWorkTime(newWorkTime);
		return enrolment;
	}

	@Override
	public void unbind(final Activity object) {
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Nature.class, object.getType());

		tuple = super.unbind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
