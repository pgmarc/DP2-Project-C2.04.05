
package acme.features.authenticated.auditor;

import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedAuditorRepository extends AbstractRepository {

	//	// Auth Auditors
	//
	//	@Query("SELECT a FROM Audit a WHERE a.course.id = :courseId")
	//	Collection<Audit> findAuditByCourse(String courseId);
	//
	//	@Query("SELECT a, a.auditor FROM Audit WHERE a.course.id = :courseId AND a.id = :auditId")
	//	Audit findAuditDetailsByCourse(String courseId, String auditId);
	//
	//	// Auditors + Audits
	//
	//	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :auditorId")
	//	Collection<Audit> findAuditByAuditor(String auditorId);
	//
	//	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :auditorId AND a.id = :auditId")
	//	Audit findAuditDetailsByAuditor(String auditorId, String auditId);
	//
	//	// AuditRecord + Auditors
	//
	//	@Query("SELECT ar FROM AuditRecord WHERE ar.audit.auditor.id = :auditorId")
	//	Collection<AuditRecord> findAuditRecordByAuditor(String auditorId);
	//
	//	@Query("SELECT ar FROM AuditRecord WHERE ar.audit.auditor.id = :auditorId AND ar.id = :auditRecordId")
	//	AuditRecord findAuditRecordDetails(String auditorId, String auditRecordId);

}
