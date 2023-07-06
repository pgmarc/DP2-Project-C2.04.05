
package acme.forms;

import java.util.Map;

import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.framework.data.AbstractRole;
import acme.helpers.Statistic;

public class AdministratorDashboard extends AbstractForm {

	private static final long				serialVersionUID	= 1L;

	@NotNull
	protected Map<AbstractRole, Integer>	numberOfPrincipalWithRole;

	protected double						peepsRatioEmailAndLink;

	protected double						criticalBulletinsRatio;

	protected double						nonCriticalBulletinsRatio;

	@NotNull
	protected Map<String, Statistic>		budgetStatistics;

	@NotNull
	protected Statistic						noteStatistics;

}
