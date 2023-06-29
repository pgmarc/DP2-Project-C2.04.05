
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String instantiationMoment, final String title, final String nick, final String message, final String email, final String moreInfo) {

		super.clickOnMenu("Anonymous", "List peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, nick);
		super.checkColumnHasValue(recordIndex, 1, instantiationMoment);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("instantiationMoment", instantiationMoment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

	}

	// No negative cases involiving a list operation since there is not input data involved

	@Test
	public void test101Positive() {

		super.signIn("administrator1", "administrator1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.clickOnMenu("Authenticated", "List peeps");
		super.checkListingExists();
		super.clickOnButton("Create peep");
		super.checkFormExists();
		super.signOut();
	}

}
