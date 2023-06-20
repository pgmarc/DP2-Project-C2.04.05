
package acme.entities.practicum;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	boolean						addendum;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			sessionAbstract;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startingDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endingDate;

	@Digits(integer = 4, fraction = 2)
	double						duration;

	@URL
	protected String			moreInfo;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Practicum			practicum;


	public double getDuration() {

		long durationInSeconds = 0;
		boolean isStartingDateBeforeEndingDate = false;

		if (this.startingDate != null && this.endingDate != null)
			isStartingDateBeforeEndingDate = MomentHelper.isBefore(this.startingDate, this.endingDate);
		if (isStartingDateBeforeEndingDate)
			durationInSeconds = MomentHelper.computeDuration(this.startingDate, this.endingDate).getSeconds();

		return Math.round(durationInSeconds / 3600.0 * 100.0) / 100.0;

	}

	public void setDuration() {
		this.duration = this.getDuration();
	}
}
