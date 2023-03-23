
package acme.features.auditor;

import org.springframework.stereotype.Controller;

import acme.entities.audit.AuditRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, AuditRecord> {
}
