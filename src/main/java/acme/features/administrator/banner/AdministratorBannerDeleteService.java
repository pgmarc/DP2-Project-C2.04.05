
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	//Internal State

	@Autowired
	protected AdministratorBannerRepository repository;

	//AbstractService Interface


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

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
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {

		super.bind(object, "lastModified", "displayStart", "displayFinish", "slogan", "picture", "target");
	}

	@Override
	public void validate(final Banner object) {
		//No need for validation
	}

	@Override
	public void perform(final Banner object) {

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {

		Tuple tuple;

		tuple = super.unbind(object, "lastModified", "displayStart", "displayFinish", "slogan", "picture", "target");

		super.getResponse().setData(tuple);
	}
}
