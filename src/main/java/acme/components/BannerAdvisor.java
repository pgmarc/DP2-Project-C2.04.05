
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.offer.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected BannerRepository repository;


	@ModelAttribute("banner")
	public Banner getAdvertisement() {
		Banner result;
		result = this.repository.findRandomBanner();

		return result;
	}

}
