
package acme.features.auditor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.AuditRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditRecord WHERE ar.audit.auditor.id = :auditorId")
	Collection<AuditRecord> findAuditRecordByAuditor(int auditorId);

	@Query("SELECT ar FROM AuditRecord WHERE ar.audit.auditor.id = :auditorId AND ar.id = :auditRecordId")
	AuditRecord findAuditRecordDetails(int auditorId, int auditRecordId);
}
