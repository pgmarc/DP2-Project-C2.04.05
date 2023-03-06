
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

	protected double			averageSessionTime;

	protected double			maximumSessionTime;

	protected double			minimumSessionTime;

	protected double			standardDeviationTime;

	protected double			averageTutorialTime;

	protected double			maximumTutorialTime;

	protected double			minimumTutorialTime;

	protected double			standardDeviationTutorialTime;
}
