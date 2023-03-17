
package acme.entities.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assesment;

	@NotBlank
	@Pattern(regexp = "(?:A\\+|A|B|C|D|F|F\\-)")
	protected String			mark;

	// initDate and endDate between 1 hour of length
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				initDate;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDate;

	@URL
	protected String			moreInfo;

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	protected Audit				audit;
}
