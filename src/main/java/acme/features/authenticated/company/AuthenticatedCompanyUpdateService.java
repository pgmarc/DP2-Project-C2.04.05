
package acme.features.authenticated.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyUpdateService extends AbstractService<Authenticated, Company> {

	@Autowired
	protected AuthenticatedCompanyRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		boolean isCompany;

		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(isCompany);
	}

	@Override
	public void load() {
		Company object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneCompanyByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Company object) {

		super.bind(object, "name", "vatNumber", "summary", "moreInfo");
	}

	@Override
	public void validate(final Company object) {
	}

	@Override
	public void perform(final Company object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final Company object) {

		Tuple tuple;

		tuple = BinderHelper.unbind(object, "name", "vatNumber", "summary", "moreInfo");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
