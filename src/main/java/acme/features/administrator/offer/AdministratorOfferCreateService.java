
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.SystemCurrency;
import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository repository;


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

		Date instantiationMoment;
		Offer offer;

		instantiationMoment = MomentHelper.getCurrentMoment();

		offer = new Offer();
		offer.setInstantiationMoment(instantiationMoment);

		super.getBuffer().setData(offer);

	}

	@Override
	public void bind(final Offer offer) {

		super.bind(offer, "heading", "summary", "startingDate", "endingDate", "price", "moreInfo");

	}

	@Override
	public void validate(final Offer offer) {

		Money price;
		SystemCurrency systemCurrency;
		String supportedCurrencies;
		List<String> currencies;
		final double minAmount = 0;
		final double maxAmount = 1_000_000;
		Date oneDaySinceInstantiationMoment;
		Date maximumAvailability;
		boolean isStartingDateBeforeEndingDate;
		boolean isEndingDateBeforeMaximumDate;
		boolean isOneWeekLong;
		boolean isOneDayAhead;
		boolean isAmountBetweenRange;
		boolean isSupportedCurrency;

		price = offer.getPrice();
		systemCurrency = this.repository.showSystemCurrency();
		supportedCurrencies = systemCurrency.getSupportedCurrencies();

		currencies = Arrays.asList(supportedCurrencies.trim().split(";"));

		if (!super.getBuffer().getErrors().hasErrors()) {

			isStartingDateBeforeEndingDate = MomentHelper.isBefore(offer.getStartingDate(), offer.getEndingDate());
			super.state(isStartingDateBeforeEndingDate, "*", "administrator.offer.form.error.endingDate-before-startingDate");

			oneDaySinceInstantiationMoment = MomentHelper.deltaFromMoment(offer.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			isOneDayAhead = MomentHelper.isAfterOrEqual(offer.getStartingDate(), oneDaySinceInstantiationMoment);
			super.state(isOneDayAhead, "*", "administrator.offer.form.error.out-minAvailability");

			maximumAvailability = new Date(4_133_977_199_000L); // 2100/12/31 23:59:59 CEST
			isEndingDateBeforeMaximumDate = MomentHelper.isBeforeOrEqual(offer.getStartingDate(), maximumAvailability);
			super.state(isEndingDateBeforeMaximumDate, "*", "administrator.offer.form.error.out-maxAvailability");

			isOneWeekLong = MomentHelper.isLongEnough(offer.getStartingDate(), offer.getEndingDate(), 1, ChronoUnit.WEEKS);
			super.state(isOneWeekLong, "*", "administrator.offer.form.error.min-duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			isAmountBetweenRange = minAmount <= price.getAmount() && price.getAmount() <= maxAmount;
			super.state(isAmountBetweenRange, "price", "administrator.offer.form.error.amount-out-of-range");

			isSupportedCurrency = currencies.contains(price.getCurrency());
			super.state(isSupportedCurrency, "price", "administrator.offer.form.error.currency-not-supported");

		}

	}

	@Override
	public void perform(final Offer offer) {

		this.repository.save(offer);
	}

	@Override
	public void unbind(final Offer offer) {

		Tuple tuple;

		tuple = super.unbind(offer, "instantiationMoment", "heading", "summary", "startingDate", "endingDate", "price", "moreInfo");

		super.getResponse().setData(tuple);

	}
}
