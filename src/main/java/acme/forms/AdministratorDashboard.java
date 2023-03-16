
package acme.forms;

import java.util.Map;

import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.helpers.Statistic;

public class AdministratorDashboard extends AbstractForm {

	private static final long			serialVersionUID	= 1L;

	protected double					peepsRatioEmailAndLink;

	@NotNull
	protected Map<String, Integer>		numberOfPrincipalWithRole;

	@NotNull
	protected Map<Boolean, Double>		bulletinRatio;

	@NotNull
	protected Map<String, Statistic>	budgetStatistics;

	@NotNull
	protected Statistic					noteStatistics;

}
