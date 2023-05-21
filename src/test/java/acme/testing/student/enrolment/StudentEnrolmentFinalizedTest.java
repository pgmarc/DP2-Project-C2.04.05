
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class StudentEnrolmentFinalizedTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalized-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Negative(final int recordIndex, final String holder, final String creditCardNumber, final String expiryDate, final String securityCode) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Finalized Enrolment");
		super.checkFormExists();
		super.fillInputBoxIn("holder", holder);
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Finalized Enrolment");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test200Hacking() {
		super.signIn("student1", "student1");
		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(1);
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/finalized", "id=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/enrolment/finalized", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/enrolment/finalized", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalized-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test300Positive(final int recordIndex, final String holder, final String creditCardNumber, final String expiryDate, final String securityCode) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 4, "Not finalized");
		super.clickOnListingRecord(recordIndex);

		super.clickOnButton("Finalized Enrolment");
		super.checkFormExists();
		super.fillInputBoxIn("holder", holder);
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Finalized Enrolment");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 4, "Finalized");
		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("Workspace");
		super.checkNotButtonExists("Delete");
		super.checkNotButtonExists("Update");
		super.checkNotButtonExists("Finalized Enrolment");
	}
}
