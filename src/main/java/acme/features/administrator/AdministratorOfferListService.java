
package acme.features.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

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

		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Principal principal;
		int userAccountId;
		Company company;
		Collection<Practicum> practica;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		practica = this.repository.findPracticaByCompanyId(company.getId());

		super.getBuffer().setData(practica);
	}

	@Override
	public void unbind(final Offer offer) {

		Tuple tuple;

		tuple = super.unbind(offer, "code", "title");

		super.getResponse().setData(tuple);
	}

}
