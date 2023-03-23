
package acme.features.any.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyBannerShowService extends AbstractService<Any, Banner> {

	//Internal State

	@Autowired
	protected AnyBannerRepository bannerRepository;

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
		Date currentDate;
		Banner banner;
		int id;

		id = super.getRequest().getData("id", int.class);
		banner = this.bannerRepository.findOneBannerById(id);
		currentDate = MomentHelper.getCurrentMoment();
		status = MomentHelper.isAfterOrEqual(banner.getDisplayFinish(), currentDate) && MomentHelper.isBeforeOrEqual(banner.getDisplayStart(), currentDate);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Banner banner;
		int id;

		id = super.getRequest().getData("id", int.class);
		banner = this.bannerRepository.findOneBannerById(id);

		super.getBuffer().setData(banner);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "lastModified", "displayStart", "displayFinish", "slogan", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
