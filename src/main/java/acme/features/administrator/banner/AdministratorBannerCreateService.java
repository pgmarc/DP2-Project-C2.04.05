
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

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
		Banner object;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();

		object = new Banner();
		object.setLastModified(currentMoment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner banner) {

		super.bind(banner, "displayStart", "displayFinish", "slogan", "picture", "target");
	}

	@Override
	public void validate(final Banner banner) {

		boolean isOneWeekLong;
		boolean isDisplayStartingDateBeforeDisplayFinishDate;
		final boolean isDisplayStartAfterInstatiationMoment;
		final boolean isDisplayFinishAfterInstatiationMoment;
		final Date instantiationMoment = banner.getLastModified();
		final Date displayStart = banner.getDisplayStart();
		final Date displayFinish = banner.getDisplayFinish();

		if (!super.getBuffer().getErrors().hasErrors("displayStart") && !super.getBuffer().getErrors().hasErrors("displayFinish")) {

			isDisplayStartAfterInstatiationMoment = MomentHelper.isAfter(displayStart, instantiationMoment);
			isDisplayFinishAfterInstatiationMoment = MomentHelper.isAfter(displayFinish, instantiationMoment);
			super.state(isDisplayStartAfterInstatiationMoment && isDisplayFinishAfterInstatiationMoment, "*", "administrator.banner.error.after-instantiation-moment");

			isDisplayStartingDateBeforeDisplayFinishDate = MomentHelper.isBefore(displayStart, displayFinish);
			super.state(isDisplayStartingDateBeforeDisplayFinishDate, "*", "administrator.banner.error.displayFinish-before-displayStart");

			isOneWeekLong = MomentHelper.isLongEnough(displayStart, displayFinish, 1, ChronoUnit.WEEKS);
			super.state(isOneWeekLong, "*", "administrator.banner.error.short-display-period");
		}

	}

	@Override
	public void perform(final Banner banner) {

		this.repository.save(banner);
	}

	@Override
	public void unbind(final Banner banner) {

		Tuple tuple;

		tuple = super.unbind(banner, "lastModified", "displayStart", "displayFinish", "slogan", "picture", "target");

		super.getResponse().setData(tuple);
	}
}
