
package acme.features.authenticated.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferListService extends AbstractService<Authenticated, Offer> {

	@Autowired
	private AuthenticatedOfferRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Date currentMoment;
		Collection<Offer> offers;
		currentMoment = MomentHelper.getCurrentMoment();

		offers = this.repository.findOffers(currentMoment);

		super.getBuffer().setData(offers);
	}

	@Override
	public void unbind(final Offer offer) {

		Tuple tuple;

		tuple = super.unbind(offer, "startingDate", "endingDate", "heading");

		super.getResponse().setData(tuple);
	}

}
