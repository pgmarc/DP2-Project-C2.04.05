
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


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
		final Collection<Banner> banners;

		banners = this.repository.findAllBanners();

		super.getBuffer().setData(banners);
	}

	@Override
	public void unbind(final Banner object) {

		Tuple tuple;

		tuple = super.unbind(object, "lastModified", "displayStart", "displayFinish", "slogan", "picture", "target");

		super.getResponse().setData(tuple);
	}
}
