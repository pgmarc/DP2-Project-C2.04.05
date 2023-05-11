
package acme.features.authenticated.offer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	@Autowired
	private AuthenticatedOfferRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		int offerId;
		Offer offer;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId, currentMoment);

		status = offer != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int offerId;
		Offer offer;
		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();
		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId, currentMoment);

		super.getBuffer().setData(offer);
	}

	@Override
	public void unbind(final Offer object) {

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "startingDate", "startingDate", "endingDate", "price", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
