
package acme.testing.company.session;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecord, final int sessionRecord, final String title, final String sessionAbstract, final String startingDate, final String endingDate, final String moreInformation) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();
		super.clickOnButton("View scheduled sessions");

		super.clickOnListingRecord(sessionRecord);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("sessionAbstract", sessionAbstract);
		super.checkInputBoxHasValue("startingDate", startingDate);
		super.checkInputBoxHasValue("endingDate", endingDate);
		super.checkInputBoxHasValue("moreInfo", moreInformation);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/show-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String username, final String practicumCode) {
		Collection<PracticumSession> sessions;
		String param;
		sessions = this.repository.findPracticumSessionsByCompanyUsernameAndPracticumCode(username, practicumCode);
		assert sessions.size() != 0;

		for (final PracticumSession practicumSession : sessions) {
			param = String.format("id=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
