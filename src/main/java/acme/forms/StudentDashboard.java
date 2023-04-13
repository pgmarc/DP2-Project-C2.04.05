
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -----------------------------------------------

	protected int				numberOfTheoryActivities;

	protected int				numberOfHandsOnActivities;

	protected int				numberOfBalancedActivities;

	protected double			averageWorkbook;

	protected double			maximumWorkbook;

	protected double			minimumWorkbook;

	protected double			deviationWorkbook;

	protected double			averageCourse;

	protected double			maximumCourse;

	protected double			minimumCourse;

	protected double			deviationCourse;

}
