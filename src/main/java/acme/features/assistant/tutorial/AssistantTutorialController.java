
package acme.features.assistant.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Tutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialController extends AbstractController<Assistant, Tutorial> {

	//Internal State

	@Autowired
	protected AssistantTutorialShowService		showService;

	@Autowired
	protected AssistantTutorialListMineService	listMineService;

	@Autowired
	protected AssistantTutorialCreateService	createService;

	@Autowired
	protected AssistantTutorialDeleteService	deleteService;

	@Autowired
	protected AssistantTutorialUpdateService	updateService;

	@Autowired
	protected AssistantTutorialPublishService	publishService;

	//Constructors


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listMineService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("publish", "update", this.listMineService);

	}
}
