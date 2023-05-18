
package acme.testing.auditor.auditrecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditRecordShowTest extends TestHarness {

	private String	auditPath	= null;
	private String	auditQuery	= null;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditrecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
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
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assesment", assesment);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("endDate", endDate);

		if (this.auditPath == null) {
			this.auditPath = super.getCurrentPath();
			this.auditQuery = super.getCurrentQuery();
			this.auditQuery = this.auditQuery.substring(1, this.auditQuery.length());
		}

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
