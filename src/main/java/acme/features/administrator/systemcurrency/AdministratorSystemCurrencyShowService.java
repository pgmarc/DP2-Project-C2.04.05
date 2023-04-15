
package acme.features.administrator.systemcurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.SystemCurrency;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemCurrencyShowService extends AbstractService<Administrator, SystemCurrency> {

	@Autowired
	protected AdministratorSystemCurrencyRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SystemCurrency object = this.repository.showSystemCurrency();
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SystemCurrency object) {
		Tuple tuple;
		tuple = super.unbind(object, "currentCurrency", "supportedCurrencies");

		super.getResponse().setData(tuple);
	}
}
