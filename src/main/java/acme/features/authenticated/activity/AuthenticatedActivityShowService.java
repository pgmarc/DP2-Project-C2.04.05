
package acme.features.authenticated.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedActivityShowService extends AbstractService<Authenticated, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedActivityRepository repository;

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
		final Activity object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		Tuple tuple;
		tuple = super.unbind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate", "enrolment");

		super.getResponse().setData(tuple);
	}
}
