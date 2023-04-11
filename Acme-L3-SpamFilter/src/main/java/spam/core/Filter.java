
package spam.core;

import java.util.Collection;

public class Filter {

	private Filter() {
	}

	public static boolean hasSpam(final String input, final Collection<String> dictionary, final double treeshold) {
		final String formattedInput = input.trim().replaceAll("\\s+", " ").toLowerCase();
		final int numOfWords = formattedInput.split(" ").length;
		int spamCounter = 0;
		for (final String dangerousWord : dictionary)
			if (formattedInput.contains(dangerousWord)) {
				if (++spamCounter / (double) numOfWords < treeshold)
					continue;
				return true;
			}
		return false;
	}
}
