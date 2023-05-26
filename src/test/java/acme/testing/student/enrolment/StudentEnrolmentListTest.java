
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class StudentEnrolmentListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String motivation, final String courseCode, final String courseTitle, final String state) {

		super.signIn("student2", "student2");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, motivation);
		super.checkColumnHasValue(recordIndex, 2, courseCode);
		super.checkColumnHasValue(recordIndex, 3, courseTitle);
		super.checkColumnHasValue(recordIndex, 4, state);

		super.signOut();
	}

	@Test
	void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/enrolment/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/list");
		super.checkPanicExists();
		super.signOut();
	}
}
