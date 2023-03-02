
package acme.entities.practicum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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
	protected String			abstrac;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	protected boolean			draftMode;

	//@Transient
	protected Double			sessionsTotalOfHours;

	//@Transient
	protected Double			sessionsTotalOfHoursPlusTenPercent;

	//@Transient
	protected Double			sessionsTotalOfHoursMinusTenPercent;

}
