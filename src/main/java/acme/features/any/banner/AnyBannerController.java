
package acme.features.any.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyBannerController extends AbstractController<Any, Banner> {

	//Internal State

	@Autowired
	protected AnyBannerShowService bannerShowService;

	//Constructors


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.bannerShowService);
	}

}
