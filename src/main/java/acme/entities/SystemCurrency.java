
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemCurrency extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
  
	@Length(min = 3, max = 3)
	@NotBlank
	@Value("EUR")
	protected String			currentCurrency;

	@NotBlank
	@Value("EUR;USD;GBP")
	protected String			supportedCurrencies;
}
