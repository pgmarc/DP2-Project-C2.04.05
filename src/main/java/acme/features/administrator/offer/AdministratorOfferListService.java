
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferListService extends AbstractService<Administrator, Offer> {

	@Autowired
	private AdministratorOfferRepository repository;


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

		Collection<Offer> offers;

		offers = this.repository.findAllOffers();

		super.getBuffer().setData(offers);

	}

	@Override
	public void unbind(final Offer offer) {

		Tuple tuple;

		tuple = super.unbind(offer, "startingDate", "endingDate", "heading");

		super.getResponse().setData(tuple);
	}

}
