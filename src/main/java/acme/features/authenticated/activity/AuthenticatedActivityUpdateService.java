
package acme.features.authenticated.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.enumerates.Nature;
import acme.features.authenticated.enrolment.AuthenticatedEnrolmentRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedActivityUpdateService extends AbstractService<Authenticated, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedActivityRepository	repository;

	@Autowired
	protected AuthenticatedEnrolmentRepository	enrolmentRepository;

	// AbstractService interface ----------------------------------------------ç

	protected double							workTimeAnterior;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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

		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) * 10 / 6;
		this.workTimeAnterior = horas + minutosEnPorcentaje / 100;

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

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDate(), object.getEndDate()), "*", "Fecha final debe estar después de fecha inicial");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;
		Enrolment enrolment;
		int enrolmentId;
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) * 10 / 6;
		enrolmentId = object.getEnrolment().getId();
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		enrolment.setWorkTime(enrolment.getWorkTime() - this.workTimeAnterior + horas + minutosEnPorcentaje / 100);

		this.repository.save(object);
		this.enrolmentRepository.save(enrolment);
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
