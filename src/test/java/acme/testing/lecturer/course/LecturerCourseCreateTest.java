/*
 * EmployerJobCreateTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String courseAbstract, final String price, final String moreInfo) {
		// HINT: this test authenticates as an employer and then lists his or her
		// HINT: jobs, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Courses", "My courses");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Courses", "My courses");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("courseAbstract", courseAbstract);
		super.checkInputBoxHasValue("retailPrice", price);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		super.clickOnButton("Lectures");

		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String courseAbstract, final String price, final String moreInfo) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Courses", "My courses");

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();
	}

}
