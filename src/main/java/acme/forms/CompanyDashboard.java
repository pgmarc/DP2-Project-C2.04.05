
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected int[]				practicaPerCourseLastYear;

	private double				sessionsPeriodLengthAveragePerPractica;

	private double				sessionsPeriodLengthDeviationPerPractica;

	private double				sessionsPeriodLengthMinimumPerPractica;

	private double				sessionsPeriodLengthMaximunPerPractica;

	protected double			practicaPeriodLengthAverage;

	protected double			practicaPeriodLengthDeviation;

	protected double			practicaPeriodLengthMinimun;

	protected double			practicaPeriodLengthMaximun;

}
