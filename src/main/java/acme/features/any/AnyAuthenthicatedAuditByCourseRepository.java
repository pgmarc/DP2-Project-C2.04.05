
package acme.features.any;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AnyAuthenthicatedAuditByCourseRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.course.id = :courseId")
	Collection<Audit> findAuditsByCourse(int courseId);

	@Query("SELECT a FROM Audit a WHERE a.id = :auditId AND a.course.id = :courseId")
	Audit findAuditByAuditor(int auditId, int courseId);

}
