
package acme.testing.company.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {

		//You have to hardcode in the future positions of the practicum in relation to the sorting
		// When you delete a row in the table all the practicum after it change record index
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete practicum");

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

	}

}
