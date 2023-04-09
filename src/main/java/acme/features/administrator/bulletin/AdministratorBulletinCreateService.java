
package acme.features.administrator.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Bulletin;
import acme.features.authenticated.bulletin.AuthenticatedBulletinRepository;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository repository;

	// AbstractService<Authenticated, Activity> ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Bulletin object;

		object = new Bulletin();
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;

		super.bind(object, "moreInfo", "critical", "message", "title");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		Tuple tuple;

		tuple = super.unbind(object, "moreInfo", "critical", "message", "title");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
