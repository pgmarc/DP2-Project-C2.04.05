
package acme.features.assistant.tutorialsession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.TutorialSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialSessionController extends AbstractController<Assistant, TutorialSession> {

	//Internal State

	@Autowired
	protected AssistantTutorialSessionShowService		showService;

	@Autowired
	protected AssistantTutorialSessionListMineService	listMineService;

	@Autowired
	protected AssistantTutorialSessionCreateService		createService;

	@Autowired
	protected AssistantTutorialSessionDeleteService		deleteService;

	@Autowired
	protected AssistantTutorialSessionUpdateService		updateService;

	//Constructors


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listMineService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

	}
}
