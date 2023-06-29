
package acme.testing.company.session;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionListTest extends TestHarness {

	@Autowired
	private CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int sessionRecordIndex, final String startingDate, final String endingDate, final String title, final String addendum) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("View scheduled sessions");

		super.checkListingExists();
		super.checkColumnHasValue(sessionRecordIndex, 0, startingDate);
		super.checkColumnHasValue(sessionRecordIndex, 1, endingDate);
		super.checkColumnHasValue(sessionRecordIndex, 2, title);
		super.checkColumnHasValue(sessionRecordIndex, 3, addendum);

		super.signOut();

	}

	// No negative cases involving a list operation since there is not input data involved

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/list-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String username, final String practicumCode) {

		Collection<PracticumSession> sessions;
		String param;
		sessions = this.repository.findPracticumSessionsByCompanyUsernameAndPracticumCode(username, practicumCode);
		assert sessions.size() != 0;

		for (final PracticumSession practicumSession : sessions) {
			param = String.format("practicumId=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/list", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
