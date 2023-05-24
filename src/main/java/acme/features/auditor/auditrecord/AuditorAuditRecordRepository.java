
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.audit.id = :id")
	Collection<AuditRecord> findManyAuditRecordsByAuditId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar.audit from AuditRecord ar where ar.id = :id")
	Audit findOneAuditRecordByAuditId(int id);

}
