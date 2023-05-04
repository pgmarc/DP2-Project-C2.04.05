
package acme.helpers;

import javax.validation.constraints.NotNull;

import acme.features.audit.auditor.dashboard.AuditorDashboardRepository;
import acme.forms.AuditorDashboard;
import lombok.Getter;

@Getter
public class Statistic {

	@NotNull
	protected double	minimum		= 0;

	@NotNull
	protected double	maximum		= 0;

	protected double	average		= 0;

	protected double	deviation	= 0;


	public static AuditorDashboard getAuditStatistics(final int auditorId, final AuditorDashboardRepository repository) {
		final Statistic auditStats = new Statistic();
		final Integer minimum = repository.findMinAuditRecordByAuditor(auditorId);
		final Integer maximum = repository.findMaxAuditRecordByAuditor(auditorId);
		final Double average = repository.findAverageAuditRecordByAuditor(auditorId);
		//final Double deviation = repository.findDeviationAuditRecordByAuditor(auditorId);
		final Double deviation = 0.;

		if (minimum != null)
			auditStats.minimum = minimum;
		if (maximum != null)
			auditStats.maximum = maximum;
		if (average != null)
			auditStats.average = average;
		if (deviation != null)
			auditStats.deviation = deviation;

		// Statistic duration audit records
		final Statistic auditRecordLenghts = new Statistic();
		final Double durationMinimum = repository.findMinAuditRecordDurationByAuditor(auditorId);
		final Double durationMaximum = repository.findMaxAuditRecordDurationByAuditor(auditorId);
		final Double durationAverage = repository.findAverageAuditRecordDurationByAuditor(auditorId);
		//		final Double durationDeviation = repository.findDeviationAuditRecordDurationByAuditor(auditorId);

		if (durationMinimum != null)
			auditRecordLenghts.minimum = durationMinimum;
		if (durationMaximum != null)
			auditRecordLenghts.maximum = durationMaximum;
		if (durationAverage != null)
			auditRecordLenghts.average = durationAverage;
		//		if ( durationDeviation != null ) auditRecordLenghts.deviation = durationDeviation;

		Integer totalTheory, totalOnHands;
		totalTheory = repository.findNumberOfTheoryCoursesByAuditor(auditorId);
		totalOnHands = repository.findNumberOfHandsOnCoursesByAuditor(auditorId);

		final AuditorDashboard ad = new AuditorDashboard();
		ad.setTheoryAudits(totalTheory == null ? 0 : totalTheory);
		ad.setHandOnAudits(totalOnHands == null ? 0 : totalOnHands);
		ad.setAuditStats(auditStats);
		ad.setAuditRecordStats(auditRecordLenghts);

		return ad;
	}

}
