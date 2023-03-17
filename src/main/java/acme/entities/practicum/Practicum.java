
package acme.entities.practicum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
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

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				startingDate;

	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				endingDate;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@Digits(integer = 4, fraction = 2)
	@DecimalMin(value = "168.00")
	protected double			estimatedTotalTime;

	@Digits(integer = 4, fraction = 2)
	@DecimalMin(value = "168.00")
	@Transient
	protected double			practicaPeriodLength;

}
