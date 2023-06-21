
package acme.testing.company.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.practicum.PracticumSession;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("SELECT ps from PracticumSession ps WHERE ps.practicum.company.userAccount.username = :username AND ps.practicum.code = :code")
	Collection<PracticumSession> findPracticumSessionsByCompanyUsernameAndPracticumCode(String username, String code);

}
