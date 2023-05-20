
package acme.testing.authenticated.note;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuthenticatedNoteListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/note/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String instantiationMoment, final String title) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Notes");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, instantiationMoment);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.signOut();
	}

	@Test
	void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/authenticated/note/list");
		super.checkPanicExists();
	}
}
