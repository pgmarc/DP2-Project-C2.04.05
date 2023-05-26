
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferDeleteService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		int offerId;
		Offer offer;
		boolean status;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(offerId);

		status = offer != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

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
	public void bind(final Offer practicum) {
	}

	@Override
	public void validate(final Offer practicum) {

	}

	@Override
	public void perform(final Offer practicum) {

		this.repository.delete(practicum);
	}

	@Override
	public void unbind(final Offer practicum) {

	}

}
