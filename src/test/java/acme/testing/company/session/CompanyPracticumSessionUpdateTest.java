
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

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int practicumIndex, final String title, final String sessionAbstract, final String startingDate, final String endingDate, final String moreInfo) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumIndex);

		super.checkFormExists();
		super.clickOnButton("View scheduled sessions");
		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sessionAbstract", sessionAbstract);
		super.fillInputBoxIn("startingDate", startingDate);
		super.fillInputBoxIn("endingDate", endingDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Update session");

		super.checkListingExists();
		super.checkNotListingEmpty();
		super.checkColumnHasValue(recordIndex, 0, startingDate);
		super.checkColumnHasValue(recordIndex, 1, endingDate);
		super.checkColumnHasValue(recordIndex, 2, title);
		super.checkColumnHasValue(recordIndex, 3, "-");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int sessionIndex, final int practicumIndex, final String title, final String sessionAbstract, final String startingDate, final String endingDate, final String moreInfo) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumIndex);

		super.checkFormExists();
		super.clickOnButton("View scheduled sessions");
		super.clickOnListingRecord(sessionIndex);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("sessionAbstract", sessionAbstract);
		super.fillInputBoxIn("startingDate", startingDate);
		super.fillInputBoxIn("endingDate", endingDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Update session");
		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking(final String username, final String practicumCode) {

		final Collection<PracticumSession> sessions = this.repository.findPracticumSessionsByCompanyUsernameAndPracticumCode(username, practicumCode);
		final List<PracticumSession> limitedSessions = new ArrayList<PracticumSession>(sessions);
		String param;

		for (final PracticumSession session : limitedSessions.stream().limit(2).collect(Collectors.toList())) {
			param = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
