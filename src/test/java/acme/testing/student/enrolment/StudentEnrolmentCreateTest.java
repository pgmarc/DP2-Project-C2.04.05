
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class StudentEnrolmentCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Negative(final int recordCourse, final String code, final String motivation, final String goals) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Courses", "All the courses");
		super.checkListingExists();
		super.clickOnListingRecord(recordCourse);

		super.clickOnButton("Enroll");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Positive(final int recordIndex, final String code, final String motivation, final String goals, final String workTime, final int recordCourse, final String courseCode, final String courseTitle) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Courses", "All the courses");
		super.checkListingExists();
		super.clickOnListingRecord(recordCourse);

		super.clickOnButton("Enroll");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, motivation);
		super.checkColumnHasValue(recordIndex, 2, courseCode);
		super.checkColumnHasValue(recordIndex, 3, courseTitle);
		super.checkColumnHasValue(recordIndex, 4, "Not finalized");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("workTime", workTime);
		super.checkInputBoxHasValue("courseCode", courseCode);
		super.checkInputBoxHasValue("courseTitle", courseTitle);

		super.checkButtonExists("Finalized Enrolment");

		super.signOut();
	}

	@Test
	void test300Hacking() {

		super.signIn("student1", "student1");
		super.clickOnMenu("Courses", "All the courses");
		super.clickOnListingRecord(0);
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.request("/student/enrolment/create", "courseId=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/create", "courseId=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/enrolment/create", "courseId=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
