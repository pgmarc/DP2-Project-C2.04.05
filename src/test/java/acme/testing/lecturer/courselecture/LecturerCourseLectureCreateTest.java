
package acme.testing.lecturer.courselecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class LecturerCourseLectureCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/courselecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String course, final String lecture) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Add lectures to courses");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "Add lectures to courses");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, course);
		super.checkColumnHasValue(recordIndex, 1, lecture);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("lecture", lecture);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/courselecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String course, final String lecture) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Add lectures to courses");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
