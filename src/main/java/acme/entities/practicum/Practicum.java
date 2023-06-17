
package acme.entities.practicum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			practicumAbstract;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	protected boolean			draftMode;

	protected Date				startingDate;

	protected Date				endingDate;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company			company;

	@Digits(integer = 4, fraction = 2)
	protected double			estimatedTotalTime;

	@Digits(integer = 4, fraction = 2)
	protected double			practicaPeriodLength;


	public void updatePracticaPeriodLength(final Date startingDate, final Date endingDate) {
		this.setStartingDate(startingDate);
		this.setEndingDate(endingDate);
		this.setPracticaPeriodLength();
	}

	public double getPracticaPeriodLength() {

		long durationInSeconds = 0;

		if (this.startingDate != null && this.endingDate != null)
			durationInSeconds = MomentHelper.computeDuration(this.startingDate, this.endingDate).getSeconds();

		return Math.round(durationInSeconds / 3600.0 * 100.0) / 100.0;

	}

	public void setPracticaPeriodLength() {
		this.practicaPeriodLength = this.getPracticaPeriodLength();
	}
}
