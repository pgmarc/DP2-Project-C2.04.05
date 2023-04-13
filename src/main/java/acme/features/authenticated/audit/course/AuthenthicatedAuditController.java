
package acme.features.authenticated.audit.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenthicatedAuditController extends AbstractController<Authenticated, Audit> {

	@Autowired
	protected AuthenthicatedAuditListService	listService;

	@Autowired
	protected AuthenthicatedAuditShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
