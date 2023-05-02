
package acme.features.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
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
		final boolean status;
		int practicumId;
		final Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Offer practicum) {
		super.bind(practicum, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");
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

		Tuple tuple;

		tuple = super.unbind(practicum, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");

		super.getResponse().setData(tuple);
	}

}
