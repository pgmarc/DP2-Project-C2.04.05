
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

	//Internal State

	@Autowired
	protected AdministratorBannerRepository repository;

	//AbstractService Interface


	@Override
	public void check() {
		boolean status;

		status = super.getResponse().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner banner;
		int id;

		id = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(id);

		super.getBuffer().setData(banner);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "lastModified", "displayStart", "displayFinish", "slogan", "picture", "target");

		super.getResponse().setData(tuple);
	}
}
