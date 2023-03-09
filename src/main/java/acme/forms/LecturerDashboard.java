
package acme.forms;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LecturerDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected int				totalTheoryLectures;

	protected int				totalHandsOnLectures;

	@Digits(fraction = 2, integer = 3)
	protected double			minimumLearningTimeLectures;

	@Digits(fraction = 2, integer = 3)
	protected double			maximumLearningTimeLectures;

	@Digits(fraction = 2, integer = 3)
	protected double			minimumLearningTimeCourses;

	@Digits(fraction = 2, integer = 3)
	protected double			maximumLearningTimeCourses;

	protected Double			averageLectures;

	protected Double			deviationLectures;

	protected Double			averageCourses;

	protected Double			deviationCourses;
}
