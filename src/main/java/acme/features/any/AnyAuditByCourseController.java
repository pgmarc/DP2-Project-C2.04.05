
package acme.features.auditor.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.features.auditor.audit.AuditorAuditListService;
import acme.features.auditor.audit.AuditorAuditShowService;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuditorAuditController extends AbstractController<Authenticated, Audit> {

	@Autowired
	protected AuditorAuditListService	listService;

	@Autowired
	protected AuditorAuditShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
