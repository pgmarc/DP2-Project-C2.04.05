
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;
import acme.testing.lecturer.LecturerTestRepository;

class LecturerLectureListFromCourseTest extends TestHarness {

	@Autowired
	protected LecturerTestRepository	repository;

	final String						path	= "/lecturer/lecture/list-from-course";


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-from-course-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String lectureAbstract, final String nature) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Courses", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(4);

		super.checkButtonExists("Lectures");
		super.clickOnButton("Lectures");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, lectureAbstract);
		super.checkColumnHasValue(recordIndex, 2, nature);

		super.signOut();
	}

	@Test
	void test300Hacking() {

		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses) {
			param = String.format("masterId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request(this.path, param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request(this.path, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request(this.path, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request(this.path, param);
			super.checkPanicExists();
			super.signOut();

		}
	}
}
