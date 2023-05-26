
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class StudentActivityUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String abstract$, final String moreInfo, final String type, final String startDate, final String endDate) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstract$);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final String title, final String abstract$, final String moreInfo, final String type, final String startDate, final String endDate) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {

		super.signIn("student1", "student1");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/activity/update", "id=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activity/update", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/update", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
