
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String practicumAbstract, final String goals, final String draftMode, final String startingDate, final String endingDate, final String estimatedTotalTime,
		final String practicaPeriodLength) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("practicumAbstract", practicumAbstract);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("draftMode", draftMode);
		super.checkInputBoxHasValue("startingDate", startingDate);
		super.checkInputBoxHasValue("endingDate", endingDate);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		super.checkInputBoxHasValue("practicaPeriodLength", practicaPeriodLength);

		super.signOut();
	}

	@Test
	public void test300HackingShouldNotShowPracticumDetailsNotCompanyRole() {

		final Collection<Practicum> practica;
		String param;

		practica = this.repository.findPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	public void test301HackingShouldNotShowPracticumDetailsOfOtherCompany() {

		final Collection<Practicum> practica;
		String param;

		practica = this.repository.findPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.signIn("company2", "company2");
				super.request("/company/practicum/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
