
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import acme.enumerates.Currency;
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

	@NotNull
	@Value("${Currency.EUR}")
	protected Currency			currentCurrency;

	@Value("EUR;USD;GBP")
	protected String			supportedCurrencies;
}
