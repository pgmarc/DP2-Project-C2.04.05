
package acme.helpers;

import javax.validation.constraints.NotNull;

public class Statistic<E, T> {

	@NotNull
	protected T			minimum;

	@NotNull
	protected T			maximum;

	protected double	average;

	protected double	deviation;

}
