
package spam.core;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilterTest {

	@Test
	void test() {
		final double treeshold = .1;
		final Collection<String> dictionary = new ArrayList<String>(9);

		dictionary.add("sex");
		dictionary.add("sexo");
		dictionary.add("cialis");
		dictionary.add("nigeria");
		dictionary.add("viagra");
		dictionary.add("one million");
		dictionary.add("un millón");
		dictionary.add("you’ve won");
		dictionary.add("has ganado");

		final String exampleInputText = "Si tenemos mucho sexo, es probable que no necesitemos viagra." + "Ni en un                               millón                   \t\r de años irás a Nigeria"
			+ ".Has ganado un boleto a las Malvinas, para curarse de CIaLIS. Click aquí para entrar al sorteo";

		Assertions.assertTrue(Filter.hasSpam(exampleInputText, dictionary, treeshold));

	}

}
