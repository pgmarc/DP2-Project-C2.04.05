
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Lecture;
import acme.testing.TestHarness;
import acme.testing.lecturer.LecturerTestRepository;

class LecturerLecturePublishTest extends TestHarness {

	@Autowired
	protected LecturerTestRepository	repository;

	final String						path	= "/lecturer/lecture/publish";


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive-0.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.checkSubmitExists("Publish");
		super.clickOnSubmit("Publish");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive-1.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test101Positive(final int recordIndex, final String title) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(7);
		super.checkFormExists();
		super.checkButtonExists("Lectures");
		super.clickOnButton("Lectures");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("draftMode", "true");
		super.checkSubmitExists("Publish");
		super.clickOnSubmit("Publish");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	void test300Hacking() {
		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraftMode()) {
				param = String.format("id=%d", lecture.getId());

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
		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures)
			if (!lecture.isDraftMode()) {
				param = String.format("id=%d", lecture.getId());

				super.signIn("lecturer1", "lecturer1");
				super.request(this.path, param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
