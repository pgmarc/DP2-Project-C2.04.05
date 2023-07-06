
package acme.testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class SignUpTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/sign-up/positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final String username, final String password, final String name, final String surname, final String email) {
		super.signUp(username, password, name, surname, email);
		super.signIn(username, password);
		super.signOut();
		super.checkLinkExists("Sign in");
	}

	public void test200Negative() {
		// TODO
	}

	public void test300Hacking() {
		// TODO
	}
}
