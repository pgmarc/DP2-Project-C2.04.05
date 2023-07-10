
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	final String path = "/assistant/tutorial/create";


	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String title, final String abstrac, final String goals, final String estimatedHours, final String assistantName) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstrac", abstrac);
		super.fillInputBoxIn("goals", goals);
		super.checkInputBoxHasValue("assistantName", assistantName);

		super.checkSubmitExists("Create");
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();

		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstrac", abstrac);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedHours", estimatedHours);
		super.checkInputBoxHasValue("assistantName", assistantName);

		super.checkButtonExists("Sessions");
		super.clickOnButton("Sessions");
		super.checkNotPanicExists();
		super.checkListingExists();
		super.checkNotListingEmpty();

		super.signOut();
	}

	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordIndex, final String code, final String title, final String abstrac, final String goals) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstrac", abstrac);
		super.fillInputBoxIn("goals", goals);

		super.checkSubmitExists("Create");
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.checkCurrentPath("/assistant/tutorial/create");

		super.signOut();
	}

	@Test
	public void test300hacking() {

		super.checkLinkExists("Sign in");
		super.request(this.path);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request(this.path);
		super.checkPanicExists();
		super.signOut();
	}
}
