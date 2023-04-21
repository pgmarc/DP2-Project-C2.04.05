
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.course.id = :id and a.draftMode = false")
	Collection<Audit> findManyAuditsByCourseId(int id);

	@Query("select a from Audit a where a.id = :id and a.draftMode = false")
	Audit findOneAuditById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select ar.mark from AuditRecord ar where ar.audit.id = :id group by ar.mark order by count(ar.mark)")
	Collection<String> getMarkByAudit(int id);
}
