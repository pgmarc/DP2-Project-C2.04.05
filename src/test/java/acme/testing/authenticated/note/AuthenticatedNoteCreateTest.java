
package acme.testing.authenticated.note;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuthenticatedNoteCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/note/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String instantiationMoment, final String title, final String fullName, final String message, final String email, final String moreInfo) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Authenticated", "List notes");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Authenticated", "List notes");
		super.checkListingExists();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/note/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String instantiationMoment, final String title, final String fullName, final String message, final String email, final String moreInfo) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Authenticated", "List notes");

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/authenticated/note/create");
		super.checkPanicExists();
	}

}
