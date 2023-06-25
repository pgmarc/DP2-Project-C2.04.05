
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String instantiationMoment, final String nick, final String title) {

		super.clickOnMenu("Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, instantiationMoment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 1, title);

	}

	// No negative cases involiving a list operation since there is not input data involved

	@Test
	public void test101Positive() {

		super.checkLinkExists("Sign in");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();

		super.signIn("administrator1", "administrator1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/any/peep/list");
		super.checkListingExists();
		super.request("/any/peep/publish");
		super.checkFormExists();
		super.signOut();
	}

}
