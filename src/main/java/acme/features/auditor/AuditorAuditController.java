
package acme.features.auditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditController extends AbstractController<Auditor, Audit> {

	@Autowired
	protected AuditorAuditListService	listAudits;

	@Autowired
	protected AuditorAuditShowService	showAudit;

	@Autowired
	protected AuditorAuditCreateService	createAudit;

	@Autowired
	protected AuditorAuditUpdateService	updateAudit;

	@Autowired
	protected AuditorAuditDelete		deleteAudit;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAudits);
		super.addBasicCommand("show", this.showAudit);
		super.addBasicCommand("create", this.createAudit);
		super.addBasicCommand("update", this.updateAudit);
		super.addBasicCommand("delete", this.deleteAudit);
	}
}
