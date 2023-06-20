
package acme.testing.company.session;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish practicum");
		super.checkNotErrorsExist();

		// Checks if publish button is disabled
		super.clickOnListingRecord(recordIndex);
		super.checkNotButtonExists("Publish practicum");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String course, final String code, final String title, final String practicumAbstract, final String goals) {
		//  This test attempts to publish a job that cannot be published, yet.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();

		super.clickOnButton("Create practicum");
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("practicumAbstract", practicumAbstract);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Create practicum");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("practicumAbstract", practicumAbstract);
		super.checkInputBoxHasValue("goals", goals);

		super.checkFormExists();
		super.clickOnSubmit("Publish practicum");
		super.checkAlertExists(false);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// This test tries to publish a Practicum with a role other than "Company".

		Collection<Practicum> practica;
		String params;

		practica = this.repository.findPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.isDraftMode()) {
				params = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// This test tries to publish a published practicum that was registered by other company.

		final Collection<Practicum> practica;
		String params;

		super.signIn("company2", "company2");
		practica = this.repository.findPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (!practicum.isDraftMode()) {
				params = String.format("id=%d", practicum.getId());
				super.request("/employer/job/publish", params);
				super.checkPanicExists();
			}
		super.signOut();
	}

}
