
package acme.entities.message;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiationMoment;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			author;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			message;

	@Email
	@Length(max = 255)
	protected String			email;

	@URL
	@Length(max = 255)
	protected String			moreInfo;

}
