package helpers;

import javax.validation.constraints.NotNull;

public class Statistic<T> {

	@NotNull
	protected T minimum;
	
	@NotNull
	protected T maximum;
	
	protected float average;
	
	protected float deviation;
	
}
