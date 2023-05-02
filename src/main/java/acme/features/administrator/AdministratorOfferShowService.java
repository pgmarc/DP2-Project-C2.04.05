
package acme.features.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Administrator;
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Practicum practicum;
		final int id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(id);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Offer object) {

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");

		super.getResponse().setData(tuple);
	}
}
