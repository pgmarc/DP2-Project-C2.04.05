
package acme.testing;

import org.junit.jupiter.api.Test;

class FavouriteLinkTest extends TestHarness {

	@Test
	void test100Positive() {
		super.requestHome();
		super.clickOnMenu("Anonymous", "77927548Y: González Marcos, Pedro");
		super.checkCurrentUrl("https://www.twitch.tv");
		super.requestHome();
		super.clickOnMenu("Anonymous", "30696569X: Mateos Gómez, Fernando José");
		super.checkCurrentUrl("https://www.youtube.com");
		super.requestHome();
		super.clickOnMenu("Anonymous", "77938703Y: Bermejo Soria, Carlos");
		super.checkCurrentUrl("https://www.youtube.com/watch?v=w-VlBV4hmcE&ab_channel=Yumirubeats%E2%99%A5slow");
		super.requestHome();
		super.clickOnMenu("Anonymous", "29504092Z: Gallardo Martos, Daniel");
		super.checkCurrentUrl("https://openai.com");
		super.requestHome();
		super.clickOnMenu("Anonymous", "32099918Z: Zarzuela Reina, Carlos");
		super.checkCurrentUrl("https://boardgamegeek.com");
	}

}
