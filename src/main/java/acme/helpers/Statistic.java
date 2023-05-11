
package acme.helpers;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import acme.features.audit.auditor.dashboard.AuditorDashboardRepository;
import acme.forms.AuditorDashboard;
import lombok.Getter;

@Getter
public class Statistic {

	@NotNull
	protected double	minimum	= 0;

	@NotNull
	protected double	maximum	= 0;

	protected double	average	= 0;

	protected double	deviation;


	private static double deviationFunction(final Collection<Integer> sample, final double average) {
		int totalElements = 1;
		double totalWeight = 0;

		for (final Integer sampleElement : sample) {
			totalElements += sampleElement;
			totalWeight += Math.pow(sampleElement - average, 2);
		}
		return totalElements > 1 ? Math.sqrt(totalWeight / (totalElements - 1)) : 0;

	}

	public static AuditorDashboard getAuditStatistics(final int auditorId, final AuditorDashboardRepository repository) {
		final Statistic auditStats = new Statistic();
		final Integer minimum = repository.findMinAuditRecordByAuditor(auditorId);
		final Integer maximum = repository.findMaxAuditRecordByAuditor(auditorId);
		final Double average = repository.findAverageAuditRecordByAuditor(auditorId);
		final double deviation = 0.;

		if (minimum != null)
			auditStats.minimum = minimum;
		if (maximum != null)
			auditStats.maximum = maximum;
		if (average != null)
			auditStats.average = average;
		auditStats.deviation = Statistic.deviationFunction(repository.findDeviationAuditRecordByAuditor(auditorId), average);

		// Statistic duration audit records
		final Statistic auditRecordLenghts = new Statistic();
		final Double durationMinimum = repository.findMinAuditRecordDurationByAuditor(auditorId);
		final Double durationMaximum = repository.findMaxAuditRecordDurationByAuditor(auditorId);
		final Double durationAverage = repository.findAverageAuditRecordDurationByAuditor(auditorId);

		if (durationMinimum != null)
			auditRecordLenghts.minimum = durationMinimum;
		if (durationMaximum != null)
			auditRecordLenghts.maximum = durationMaximum;
		if (durationAverage != null)
			auditRecordLenghts.average = durationAverage;
		auditRecordLenghts.deviation = Statistic.deviationFunction(repository.findDeviationAuditRecordDurationByAuditor(auditorId), durationAverage);

		Integer totalTheory, totalOnHands, totalBalanced;
		totalTheory = repository.findNumberOfTheoryCoursesByAuditor(auditorId);
		totalOnHands = repository.findNumberOfHandsOnCoursesByAuditor(auditorId);
		totalBalanced = repository.findNumberOfBalancedCoursesByAuditor(auditorId);

		final AuditorDashboard ad = new AuditorDashboard();
		ad.setTheoryAudits(totalTheory == null ? 0 : totalTheory);
		ad.setHandOnAudits(totalOnHands == null ? 0 : totalOnHands);
		ad.setBalancedAudits(totalBalanced == null ? 0 : totalBalanced);
		ad.setAuditStats(auditStats);
		ad.setAuditRecordStats(auditRecordLenghts);

		return ad;
	}

}
