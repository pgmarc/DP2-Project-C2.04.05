
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected int[]				practicaPerCourseLastYear;

	private Double				sessionsPeriodLengthAveragePerPractica;

	private Double				sessionsPeriodLengthDeviationPerPractica;

	private Double				sessionsPeriodLengthMinimumPerPractica;

	private Double				sessionsPeriodLengthMaximunPerPractica;

	protected double			practicaPeriodLengthAverage;

	protected double			practicaPeriodLengthDeviation;

	protected double			practicaPeriodLengthMinimun;

	protected double			practicaPeriodLengthMaximun;

}
