
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -----------------------------------------------

	protected int				totalNumberOfTutorials;

	protected Double			averageSessionTime;

	protected Double			maximumSessionTime;

	protected Double			minimumSessionTime;

	protected Double			standardDeviationTime;

	protected Double			averageTutorialTime;

	protected Double			maximumTutorialTime;

	protected Double			minimumTutorialTime;

	protected Double			standardDeviationTutorialTime;
}
