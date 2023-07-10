
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class StudentActivityListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String abstract$) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Courses", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstract$);

		super.signOut();
	}

	@Test
	void test300Hacking() {

		super.signIn("student1", "student1");

		super.clickOnMenu("Courses", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.clickOnButton("Create");
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/activity/list", "id=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activity/list", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/list", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
