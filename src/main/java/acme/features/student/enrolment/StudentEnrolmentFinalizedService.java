
package acme.features.student.enrolment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinalizedService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Enrolment enrolment;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(student);

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
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "holder", "creditCardNumber");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("holder"))
			super.state(object.getHolder() != null && !object.getHolder().equals(""), "holder", "authenticated.enrolment.list.label.validate.holder");

		if (!super.getBuffer().getErrors().hasErrors("creditCardNumber")) {
			final String creditCardNumber = super.getRequest().getData("creditCardNumber", String.class);
			final Pattern pattern = Pattern.compile("^(?!0+$)[0-9]{12,19}$");
			final Matcher matcher = pattern.matcher(creditCardNumber);
			super.state(matcher.matches(), "creditCardNumber", "authenticated.enrolment.list.label.validate.creditCardNumber");
		}

		if (!super.getBuffer().getErrors().hasErrors("expiryDate"))
			if (super.getRequest().getLocale().getLanguage().equals("es"))
				this.validateExpiryDate("^(0?[1-9]|1[0-2])/\\d{2}$", "MM/yy");
			else
				this.validateExpiryDate("^\\d{2}/(0?[1-9]|1[0-2])$", "yy/MM");

		if (!super.getBuffer().getErrors().hasErrors("securityCode")) {
			final String securityCode = super.getRequest().getData("securityCode", String.class);
			final Pattern pattern = Pattern.compile("\\d{3}");
			final Matcher matcher = pattern.matcher(securityCode);
			super.state(matcher.matches(), "securityCode", "authenticated.enrolment.list.label.validate.securityCode");
		}
	}

	private void validateExpiryDate(final String patternExpiryDate, final String formatExpiryDate) {
		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		final Pattern pattern = Pattern.compile(patternExpiryDate);
		final Matcher matcher = pattern.matcher(expiryDate);
		super.state(matcher.matches(), "expiryDate", "authenticated.enrolment.list.label.validate.expiryDate.format");

		final SimpleDateFormat format = new SimpleDateFormat(formatExpiryDate);
		try {
			final Date date = format.parse(expiryDate);
			super.state(MomentHelper.isFuture(date), "expiryDate", "authenticated.enrolment.list.label.validate.expiryDate");
		} catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		final String creditCardNumber = super.getRequest().getData("creditCardNumber", String.class);
		final int lowerNibble = Integer.parseInt(creditCardNumber.substring(creditCardNumber.length() - 4));

		object.setDraftMode(false);
		object.setLowerNibble(lowerNibble);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "holder", "lowerNibble");
		if (super.getRequest().hasData("creditCardNumber", String.class))
			tuple.put("creditCardNumber", super.getRequest().getData("creditCardNumber", String.class));
		if (super.getRequest().hasData("expiryDate", String.class))
			tuple.put("expiryDate", super.getRequest().getData("expiryDate", String.class));
		if (super.getRequest().hasData("securityCode", String.class))
			tuple.put("securityCode", super.getRequest().getData("securityCode", String.class));
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
