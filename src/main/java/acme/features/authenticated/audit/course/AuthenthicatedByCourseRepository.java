
package acme.features.authenticated.audit.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenthicatedByCourseRepository extends AbstractRepository {

	@Query("select a from Audit a where a.course.id = :id")
	Collection<Audit> findManyAuditsByCourseId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);
}
