
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditPublishTest extends TestHarness {

	private String	auditPath	= null;
	private String	auditQuery	= null;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		if (this.auditPath == null) {
			this.auditPath = super.getCurrentPath().replaceAll("show", "publish");
			this.auditQuery = super.getCurrentQuery();
			this.auditQuery = this.auditQuery.substring(1, this.auditQuery.length());
		}

		super.clickOnSubmit("Publish");

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
