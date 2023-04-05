
package acme.features.authenticated.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolment.Activity;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedActivityController extends AbstractController<Authenticated, Activity> {

	@Autowired
	protected AuthenticatedActivityListService		listService;

	@Autowired
	protected AuthenticatedActivityShowService		showService;

	@Autowired
	protected AuthenticatedActivityCreateService	createService;

	@Autowired
	protected AuthenticatedActivityUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}
}
