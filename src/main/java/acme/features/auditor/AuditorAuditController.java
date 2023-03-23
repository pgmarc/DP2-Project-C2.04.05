
package acme.features.auditor;

import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuditorAuditController extends AbstractController<Authenticated, Audit> {

	//	@Autowired
	//	protected AuthenticatedAuditorListByCourseService	listAudits;
	//
	//	@Autowired
	//	protected AuthenticatedAuditorListByCourseService	showAudits;
	//
	//
	//	@PostConstruct
	//	protected void initialise() {
	//		super.addBasicCommand("list", this.listAudits);
	//		super.addBasicCommand("show", this.showAudits);
	//	}
}
