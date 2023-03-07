
package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import enumerates.Nature;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			lectureAbstract;

	@Digits(fraction = 2, integer = 2)
	protected double			estimatedLearningTime;

	@NotNull
	protected Nature			nature;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			body;

	@URL
	protected String			moreInfo;

	@ManyToOne
	protected Course			course;

}
