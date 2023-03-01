
package acme.forms;

import java.util.List;

import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	private List<Integer>		numberOfPracticaByMonthLastYear;

	protected Double			sessionsPeriodLenthAveragePerPractica;

	protected Double			sessionsPeriodLengthDeviationPerPractica;

	protected Double			sessionsPeriodLengthMinimumPerPractica;

	protected Double			sessionsPeriodLengthMaximunPerPractica;

	protected Double			practicaPeriodLengthAverage;

	protected Double			practicaPeriodLengthDeviation;

	protected Double			practicaPeriodLengthMinimun;

	protected Double			practicaPeriodLengthMaximun;

}
