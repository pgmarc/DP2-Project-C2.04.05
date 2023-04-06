
package acme.features.auditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRecordListService		listAuditRecords;

	@Autowired
	protected AuditorAuditRecordShowService		showAuditRecord;

	@Autowired
	protected AuditorAuditRecordCreateService	createAuditRecord;

	@Autowired
	protected AuditorAuditRecordUpdateService	updateAuditRecord;

	@Autowired
	protected AuditorAuditRecordDeleteService	deleteAuditRecord;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAuditRecords);
		super.addBasicCommand("show", this.showAuditRecord);
		super.addBasicCommand("create", this.createAuditRecord);
		super.addBasicCommand("update", this.updateAuditRecord);
		super.addBasicCommand("delete", this.deleteAuditRecord);
	}
}
