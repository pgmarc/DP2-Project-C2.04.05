
package acme.features.company.session;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanySessionRepository extends AbstractRepository {

	@Query("SELECT company FROM Company company WHERE company.userAccount.id = :id")
	Company findCompanyByUserAccountId(int id);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.id = :practicumId")
	Practicum findPracticumById(int practicumId);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :practicumId")
	Collection<PracticumSession> findSessionsByPracticumId(int practicumId);

	@Query("SELECT s FROM PracticumSession s WHERE s.id = :practicumSessionId")
	PracticumSession findPracticumSessionById(int practicumSessionId);

	@Query("SELECT count(s) FROM PracticumSession s WHERE s.practicum.id = :practicumId AND s.addendum = TRUE")
	Integer countAddendumSessions(int practicumId);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :practicumId ORDER BY s.startingDate ASC")
	List<PracticumSession> findPracticumSessionsByPracticumIdSortedByStartingDate(int practicumId);

	@Query("SELECT s FROM PracticumSession s WHERE s.practicum.id = :practicumId ORDER BY s.endingDate DESC")
	List<PracticumSession> findPracticumSessionsByPracticumIdSortedByEndingDate(int practicumId);
}
