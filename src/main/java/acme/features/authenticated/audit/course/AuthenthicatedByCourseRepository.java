
package acme.features.authenticated.audit.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenthicatedByCourseRepository extends AbstractRepository {

	@Query("select a from Audit a where a.course.id = :id")
	Collection<Audit> findManyAuditsByCourseId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);
}
