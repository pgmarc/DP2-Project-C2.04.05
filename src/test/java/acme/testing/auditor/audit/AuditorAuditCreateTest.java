
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditCreateTest extends TestHarness {

	private String	auditPath	= null;
	private String	auditQuery	= null;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String draftMode) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Authenticated", "List courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.checkButtonExists("List Audits by Course");
		super.clickOnButton("List Audits by Course");
		super.checkButtonExists("Create Audit");
		super.clickOnButton("Create Audit");

		// Create
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("draftMode", draftMode);
		super.clickOnSubmit("Create");

		if (this.auditPath == null) {
			this.auditPath = "/auditor/audit/create";
			this.auditQuery = super.getCurrentQuery();
			this.auditQuery = this.auditQuery.substring(1, this.auditQuery.length());
			this.auditPath = this.auditPath.replace("show", "create");
		}

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test200Negative(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String draftMode) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Authenticated", "List courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.checkButtonExists("List Audits by Course");
		super.clickOnButton("List Audits by Course");
		super.checkButtonExists("Create Audit");
		super.clickOnButton("Create Audit");

		// Create
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("draftMode", draftMode);
		super.clickOnSubmit("Create");
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

}
