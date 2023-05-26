
package acme.testing.auditor.auditrecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditRecordUpdateTest extends TestHarness {

	private String	auditPath	= null;
	private String	auditQuery	= null;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditrecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String subject, final String assesment, final String mark, final String initDate, final String endDate, final String moreInfo, final String auditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(Integer.parseInt(auditIndex));

		super.checkButtonExists("Audit Records");
		super.clickOnButton("Audit Records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		// Create
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("endDate", endDate);

		if (this.auditPath == null) {
			this.auditPath = super.getCurrentPath().replaceAll("show", "update");
			this.auditQuery = super.getCurrentQuery();
			this.auditQuery = this.auditQuery.substring(1, this.auditQuery.length());

		}

		super.clickOnSubmit("Update");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditrecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String subject, final String assesment, final String mark, final String initDate, final String endDate, final String moreInfo, final String auditIndex) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(Integer.parseInt(auditIndex));

		super.checkButtonExists("Audit Records");
		super.clickOnButton("Audit Records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		// Create
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("endDate", endDate);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	void test300Hacking() {
		super.signIn("student1", "student1");
		super.request(this.auditPath, this.auditQuery);
		super.checkPanicExists();

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------
}
