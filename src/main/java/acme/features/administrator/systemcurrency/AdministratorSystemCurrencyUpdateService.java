
package acme.features.administrator.systemcurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.SystemCurrency;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemCurrencyUpdateService extends AbstractService<Administrator, SystemCurrency> {

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
	public void bind(final SystemCurrency object) {

		super.bind(object, "currentCurrency", "supportedCurrencies");
	}

	@Override
	public void validate(final SystemCurrency object) {
		if (!super.getBuffer().getErrors().hasErrors("currentCurrency")) {
			final String currentCurrency = object.getCurrentCurrency();
			final String supportedCurrencies = object.getSupportedCurrencies();
			super.state(supportedCurrencies.contains(currentCurrency), "currentCurrency", "administrator.systemCurrency.form.error.notSupportedCurrency");
		}
	}

	@Override
	public void perform(final SystemCurrency object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemCurrency object) {
		Tuple tuple;
		tuple = super.unbind(object, "currentCurrency", "supportedCurrencies");

		super.getResponse().setData(tuple);
	}
}
