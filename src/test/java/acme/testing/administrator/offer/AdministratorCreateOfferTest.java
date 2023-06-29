
package acme.testing.administrator.offer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AdministratorCreateOfferTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/offer/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String instantiationMoment, final String heading, final String summary, final String startingDate, final String endingDate, final String price, final String moreInfo) {

		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "List offers");
		super.checkListingExists();

		super.clickOnButton("Create offer");
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startingDate", startingDate);
		super.fillInputBoxIn("endingDate", endingDate);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create offer");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, startingDate);
		super.checkColumnHasValue(recordIndex, 1, endingDate);
		super.checkColumnHasValue(recordIndex, 2, heading);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("instantiationMoment", instantiationMoment);
		super.checkInputBoxHasValue("heading", heading);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startingDate", startingDate);
		super.checkInputBoxHasValue("endingDate", endingDate);
		super.checkInputBoxHasValue("price", price);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

	}

	@Disabled
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/offer/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String instantiationMoment, final String heading, final String summary, final String startingDate, final String endingDate, final String price, final String moreInfo) {

		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "List offers");
		super.clickOnButton("Create offer");

		super.checkFormExists();
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("startingDate", startingDate);
		super.fillInputBoxIn("endingDate", endingDate);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create offer");

		super.checkErrorsExist();

	}
}
