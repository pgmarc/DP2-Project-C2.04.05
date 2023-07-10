
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;
import acme.testing.lecturer.LecturerTestRepository;

class LecturerCourseUpdateTest extends TestHarness {

	@Autowired
	protected LecturerTestRepository	repository;

	final String						path	= "/lecturer/course/update";


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String title, final String courseAbstract, final String price, final String moreInfo) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Update");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Submit");

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("courseAbstract", courseAbstract);
		super.checkInputBoxHasValue("retailPrice", price);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String code, final String title, final String courseAbstract, final String price, final String moreInfo) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Update");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Submit");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	void test300Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				param = String.format("id=%d", course.getId());

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

	@Test
	void test301Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				param = String.format("id=%d", course.getId());

				super.signIn("lecturer1", "lecturer1");
				super.request(this.path, param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
