
package acme.features.audit.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.helpers.Statistic;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	protected AuditorDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Auditor.class));
	}

	@Override
	public void load() {
		final int auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		final AuditorDashboard ad = Statistic.getAuditStatistics(auditorId, this.repository);

		assert ad != null;

		super.getBuffer().setData(ad);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "theoryAudits", "handOnAudits", "balancedAudits", "auditStats", "auditRecordStats");
		tuple.put("auditStatsMax", (int) object.getAuditStats().getMaximum());
		tuple.put("auditStatsMin", (int) object.getAuditStats().getMinimum());
		tuple.put("auditStatsAvg", object.getAuditStats().getAverage());
		tuple.put("auditStatsDesv", object.getAuditStats().getDeviation());

		tuple.put("auditDurationStatsMax", object.getAuditRecordStats().getMaximum());
		tuple.put("auditDurationStatsMin", object.getAuditRecordStats().getMinimum());
		tuple.put("auditDurationStatsAvg", object.getAuditRecordStats().getAverage());
		tuple.put("auditDurationStatsDesv", object.getAuditRecordStats().getDeviation());

		super.getResponse().setData(tuple);
	}
}
