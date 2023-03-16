
package acme.entities.tutorial;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.enumerates.SessionNature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstrac;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	@NotNull
	protected SessionNature		sessionNature;

	@NotNull
	protected Date				startDate;

	@NotNull
	protected Date				finishDate;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorial;

}
