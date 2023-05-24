
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

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

	}

	@Override
	public void load() {

	}

	@Override
	public void bind(final Offer offer) {

	}

	@Override
	public void validate(final Offer offer) {
	}

	@Override
	public void perform(final Offer offer) {

		this.repository.save(offer);
	}

	@Override
	public void unbind(final Offer offer) {

	}

}
