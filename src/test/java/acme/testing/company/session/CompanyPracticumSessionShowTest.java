
package acme.testing.company.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


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

	}

	@Test
	public void test301HackingShouldNotShowPracticumDetailsOfOtherCompany() {

	}

}
