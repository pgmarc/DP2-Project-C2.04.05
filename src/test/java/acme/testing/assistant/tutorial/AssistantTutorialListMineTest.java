
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialListMineTest extends TestHarness {

	final String path = "/assistant/tutorial/list";


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String title, final String code) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);

		super.checkButtonExists("Create");

		super.signOut();
	}

	@Test
	public void test300hacking() {

		super.checkLinkExists("Sign in");
		super.request(this.path);
		super.checkPanicExists();

		super.signIn("Administrator1", "administrator1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();
	}

}
