
package acme.features.administrator.systemcurrency;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.SystemCurrency;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorSystemCurrencyController extends AbstractController<Administrator, SystemCurrency> {

	@Autowired
	protected AdministratorSystemCurrencyShowService	showService;

	@Autowired
	protected AdministratorSystemCurrencyUpdateService	updateService;

	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
	}

}
