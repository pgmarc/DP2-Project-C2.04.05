
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListService extends AbstractService<Company, Practicum> {

	@Autowired
	private CompanyPracticumRepository repository;


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
		company = this.repository.findCompanyByUserAccountId(userAccountId);
		practica = this.repository.findPracticaByCompanyId(company.getId());

		super.getBuffer().setData(practica);
	}

	@Override
	public void unbind(final Practicum object) {

		Tuple tuple;

		tuple = super.unbind(object, "code", "title");

		super.getResponse().setData(tuple);
	}

}
