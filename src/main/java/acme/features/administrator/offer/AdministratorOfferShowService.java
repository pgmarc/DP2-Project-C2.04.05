
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferShowService extends AbstractService<Administrator, Offer> {

	@Autowired
	private AdministratorOfferRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		boolean offerExists;
		boolean isAdmin;
		int offerId;
		Offer offer;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId);
		offerExists = offer != null;
		isAdmin = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		status = offerExists && isAdmin;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int offerId;
		Offer offer;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId);

		super.getBuffer().setData(offer);

	}

	@Override
	public void unbind(final Offer offer) {
		Tuple tuple;

		tuple = super.unbind(offer, "instantiationMoment", "heading", "summary", "startingDate", "endingDate", "price", "moreInfo");

		super.getResponse().setData(tuple);

	}
}
