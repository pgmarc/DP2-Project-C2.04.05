/*
 * EmployerJobShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerTestRepository	repository;

	// Test data --------------------------------------------------------------

	final String						path	= "/lecturer/lecture/publish";


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive-0.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title) {
		// HINT: this test signs in as an employer, lists all of the jobs, click on  
		// HINT+ one of them, and checks that the form has the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("My lectures");
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

		super.clickOnMenu("Courses", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(6);
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
	void test200Negative() {
		//No negative testing cases for lectures publish feature
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
