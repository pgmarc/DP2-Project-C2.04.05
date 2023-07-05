
package acme.helpers;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class Statistic {

	@NotNull
	protected double	minimum		= 0;

	@NotNull
	protected double	maximum		= 0;

	protected double	average		= 0;

	protected double	deviation	= 0;
}
