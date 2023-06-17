
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticaListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// No negative cases involiving a list operation since there is not input data involved
	}

	@Test
	public void test300Hacking() {

		// Check if you can request a listing being an anonymous user
		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();

		// Check if you can request a listing being a lecturer user
		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();

		// Check if you can request a listing being an admin user
		super.signIn("administrator1", "administrator1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
