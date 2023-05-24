
package acme.testing.lecturer.courselecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class LecturerCourseLectureListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/courselecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String course, final String lecture) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Courses", "Add lectures to courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, course);
		super.checkColumnHasValue(recordIndex, 1, lecture);

		super.signOut();
	}

	@Test
	void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/lecturer/course-lecture/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/course-lecture/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course-lecture/list");
		super.checkPanicExists();
		super.signOut();
	}
}
