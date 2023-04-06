
package acme.features.authenticated.bulletin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		List<Bulletin> bulletins;

		bulletins = this.repository.findAllBulletins();

		super.getBuffer().setData(bulletins);
	}

	@Override
	public void unbind(final Bulletin object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "moreInfo", "critical", "message", "title");

		super.getResponse().setData(tuple);
	}

}
