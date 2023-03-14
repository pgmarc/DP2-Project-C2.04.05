
package acme.forms;

import java.util.Map;

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractForm;

public class CompanyDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected int[]					practicaPerCourseLastYear;

	private Map<Practicum, Double>	sessionsPeriodLengthAveragePerPractica;

	private Map<Practicum, Double>	sessionsPeriodLengthDeviationPerPractica;

	private Map<Practicum, Double>	sessionsPeriodLengthMinimumPerPractica;

	private Map<Practicum, Double>	sessionsPeriodLengthMaximunPerPractica;

	protected double				practicaPeriodLengthAverage;

	protected double				practicaPeriodLengthDeviation;

	protected double				practicaPeriodLengthMinimun;

	protected double				practicaPeriodLengthMaximun;

}
