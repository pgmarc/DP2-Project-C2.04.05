
package acme.features.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

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
		boolean status;
		int userAccountId;
		Company company;
		int practicumId;
		Practicum practicum;
		boolean practicumBelongsToCompany;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		practicumBelongsToCompany = company.getId() == practicum.getCompany().getId();

		status = practicum != null && super.getRequest().getPrincipal().hasRole(Company.class) && practicumBelongsToCompany && practicum.isDraftMode();

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
	public void bind(final Offer offer) {

		super.bind(offer, "code", "title", "practicumAbstract", "goals", "draftMode");
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
		Tuple tuple;

		tuple = super.unbind(offer, "code", "title", "practicumAbstract", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
