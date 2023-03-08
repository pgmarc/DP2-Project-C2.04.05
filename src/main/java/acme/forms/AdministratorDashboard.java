
package acme.forms;

import java.util.Map;

import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.helpers.Statistic;

public class AdministratorDashboard extends AbstractForm {

	protected double					peepsRatioEmailAndLink;

	@NotNull
	protected Map<String, Integer>		numberOfPrincipalWithRole;

	@NotNull
	protected Map<Boolean, Double>		bulletinRatio;

	// Uncomment when Currency is created
	@NotNull
	protected Map<Currency, Statistic>	budgetStatistics;

	// Requires Note class when being implemented
	@NotNull
	protected Statistic					noteStatistics;

}
