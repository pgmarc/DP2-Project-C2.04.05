
package acme.features.administrator.systemcurrency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.SystemCurrency;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSystemCurrencyRepository extends AbstractRepository {

	@Query("select sc from SystemCurrency sc")
	SystemCurrency showSystemCurrency();
}
