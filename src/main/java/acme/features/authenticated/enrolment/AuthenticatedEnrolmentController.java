
package acme.features.authenticated.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedEnrolmentController extends AbstractController<Authenticated, Enrolment> {

	@Autowired
	protected AuthenticatedEnrolmentListService		listService;

	@Autowired
	protected AuthenticatedEnrolmentShowService		showService;

	@Autowired
	protected AuthenticatedEnrolmentUpdateService	updateService;

	@Autowired
	protected AuthenticatedEnrolmentCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}
}
