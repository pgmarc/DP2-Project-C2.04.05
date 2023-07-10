
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	final String								path	= "/assistant/tutorial/update";

	@Autowired
	protected AssistantTutorialTestRepository	repository;


	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String title, final String abstrac, final String goals, final String draftMode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		if (draftMode.equals("true")) {
			super.fillInputBoxIn("code", code);
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstrac", abstrac);
			super.fillInputBoxIn("goals", goals);
			super.checkSubmitExists("Update");
			super.clickOnSubmit("Update");
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
		} else
			super.checkNotSubmitExists("Update");

		super.signOut();
	}

	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordIndex, final String code, final String title, final String abstrac, final String goals) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstrac", abstrac);
		super.fillInputBoxIn("goals", goals);

		super.checkSubmitExists("Update");
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.checkCurrentPath(this.path);

		super.signOut();
	}

	@Test
	void test300Hacking() {
		Collection<Tutorial> tutorials;
		String query;

		tutorials = this.repository.findManyTutorialsByAssitantUsername("assistant2");
		for (final Tutorial tutorial : tutorials) {
			query = String.format("id=%d", tutorial.getId());
			if (tutorial.isDraftMode()) {
				super.checkLinkExists("Sign in");
				super.request(this.path, query);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();
			} else {
				super.signIn("assistant2", "assistant2");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();
			}
		}
	}

}
