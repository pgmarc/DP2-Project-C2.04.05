/*
 * EmployerJobUpdateTest.java
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

class LecturerLectureUpdateTest extends TestHarness {

	@Autowired
	protected LecturerTestRepository	repository;

	final String						path	= "/lecturer/lecture/update";


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String lectureAbstract, final String nature, final String body, final String moreInfo) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Update");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lectureAbstract", lectureAbstract);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Submit");

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("lectureAbstract", lectureAbstract);
		super.checkInputBoxHasValue("nature", nature);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String title, final String lectureAbstract, final String nature, final String body, final String moreInfo) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Update");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lectureAbstract", lectureAbstract);
		if (nature != null)
			super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Submit");
		super.checkErrorsExist();

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
