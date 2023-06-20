
package acme.testing.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumTestRepository extends AbstractRepository {

	@Query("SELECT p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findPracticaByCompanyUsername(String username);

}
