
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String mark, final String draftMode) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, draftMode);

		super.signOut();
	}

	@Test
	void test300Hacking() {

		super.signIn("student1", "student1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
