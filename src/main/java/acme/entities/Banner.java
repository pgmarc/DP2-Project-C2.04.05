
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Past
	protected Date				lastModified;

	@NotNull
	protected Date				displayStart;

	@NotNull
	protected Date				displayFinish;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			slogan;

	@URL
	protected String			moreInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
