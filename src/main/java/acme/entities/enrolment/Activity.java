
package acme.entities.enrolment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.enumerates.Nature;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@URL
	protected String			moreInfo;

	@NotNull
	protected Nature			type;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDate;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Enrolment			enrolment;


	public double getWorkTime() {
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(this.getStartDate(), this.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (double) (MomentHelper.computeDuration(this.getStartDate(), this.getEndDate()).toMinutes() - horas * 60) / 60;
		return Math.round((horas + minutosEnPorcentaje) * 100.0) / 100.0;
	}

}
