
package acme.testing.company.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumIndex, final int sessionIndex) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumIndex);
		super.checkFormExists();

		super.clickOnButton("View scheduled sessions");
		super.clickOnListingRecord(sessionIndex);
		super.checkSubmitExists("Delete session");
		super.clickOnSubmit("Delete session");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumIndex, final int sessionIndex) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumIndex);
		super.checkFormExists();

		super.clickOnButton("View scheduled sessions");
		super.clickOnListingRecord(sessionIndex);
		super.checkNotSubmitExists("Delete session");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/delete-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String username, final String practicumCode) {

		final Collection<PracticumSession> sessions = this.repository.findPracticumSessionsByCompanyUsernameAndPracticumCode(username, practicumCode);
		final List<PracticumSession> limitedSessions = new ArrayList<PracticumSession>(sessions);
		String param;

		for (final PracticumSession session : limitedSessions.stream().limit(2).collect(Collectors.toList())) {
			param = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
