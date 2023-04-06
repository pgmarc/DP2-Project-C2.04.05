
package acme.features.auditor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :auditorId")
	Collection<Audit> findAuditByAuditor(int auditorId);

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :auditorId AND a.code = :code")
	Audit findAuditByAuditor(int auditorId, String code);
}
