
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				lastModified;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayFinish;

	@NotBlank
	@URL
	@Length(min = 1, max = 255)
	protected String			picture;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			slogan;

	@NotBlank
	@URL
	@Length(min = 1, max = 255)
	protected String			target;

}
