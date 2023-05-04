
package acme.features.audit.auditor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id AND a.course.nature = 0")
	Integer findNumberOfTheoryCoursesByAuditor(int id);

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id AND a.course.nature = 1")
	Integer findNumberOfHandsOnCoursesByAuditor(int id);

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id AND a.course.nature = 2")
	Integer findNumberOfBalancedCoursesByAuditor(int id);

	@Query("SELECT MAX( SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Integer findMaxAuditRecordByAuditor(int id);

	@Query("SELECT MIN( SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Integer findMinAuditRecordByAuditor(int id);

	@Query("SELECT AVG( SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Double findAverageAuditRecordByAuditor(int id);

	//	@Query("SELECT STDDEV( SELECT COUNT(ar) FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	//	Double findDeviationAuditRecordByAuditor(int id);

	@Query("SELECT MAX( SELECT TIMESTAMPDIFF( SECOND, ar.endDate, ar.initDate ) / 3600 FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Double findMaxAuditRecordDurationByAuditor(int id);

	@Query("SELECT MIN( SELECT TIMESTAMPDIFF( SECOND, ar.endDate, ar.initDate ) / 3600 FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Double findMinAuditRecordDurationByAuditor(int id);

	@Query("SELECT AVG( SELECT TIMESTAMPDIFF( SECOND, ar.endDate, ar.initDate ) / 3600 FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	Double findAverageAuditRecordDurationByAuditor(int id);
	//
	//	@Query("SELECT STDDEV( SELECT TIMESTAMPDIFF( SECOND, ar.endDate, ar.initDate ) / 3600 FROM AuditRecord ar WHERE ar.audit.id = a.id ) FROM Audit a WHERE a.auditor.id = :id")
	//	Double findDeviationAuditRecordDurationByAuditor(int id);
}
