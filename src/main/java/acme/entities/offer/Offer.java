
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiationMoment;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			heading;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			summary;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startingDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endingDate;

	@NotNull
	@Valid
	protected Money				price;

	@URL
	protected String			moreInfo;

}
