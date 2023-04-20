
package acme.features.authenticated.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Bulletin object = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "moreInfo", "critical", "message", "title");

		super.getResponse().setData(tuple);
	}

}
