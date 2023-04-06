
package acme.features.any;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyAuthenthicatedAuditByCourseController extends AbstractController<Authenticated, Audit> {

	@Autowired
	protected AnyAuthenthicatedAuditByCourseListService	listAuditsByCourse;

	@Autowired
	protected AnyAuthenthicatedAuditByCourseShowService	showAuditByCourse;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAuditsByCourse);
		super.addBasicCommand("show", this.showAuditByCourse);
	}
}
