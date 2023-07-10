
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	@Test
	void test100Positive() {

		super.signIn("student1", "student1");

		super.clickOnMenu("Courses", "Your Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.clickOnButton("Workspace");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.checkFormExists();
		final String currentQuery = super.getCurrentQuery().split("=")[1];

		super.clickOnSubmit("Delete");
		super.request("/student/activity/show", "id=" + currentQuery);
		super.checkPanicExists();
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
		super.clickOnListingRecord(0);
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/activity/delete", "id=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activity/delete", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/delete", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
