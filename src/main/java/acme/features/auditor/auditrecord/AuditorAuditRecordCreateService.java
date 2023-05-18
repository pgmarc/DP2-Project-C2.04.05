
package acme.features.auditor.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	private static final String				ENDDATE		= "endDate";
	private static final String				INITDATE	= "initDate";
	private static final String				MASTERID	= "masterId";

	@Autowired
	protected AuditorAuditRecordRepository	repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData(AuditorAuditRecordCreateService.MASTERID, int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData(AuditorAuditRecordCreateService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		Audit audit;
		int auditId;

		auditId = super.getRequest().getData(AuditorAuditRecordCreateService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(auditId);
		object = new AuditRecord();
		object.setAudit(audit);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		Audit audit;
		int auditId;

		auditId = super.getRequest().getData(AuditorAuditRecordCreateService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(auditId);

		super.bind(object, "subject", "assesment", "mark", AuditorAuditRecordCreateService.INITDATE, AuditorAuditRecordCreateService.ENDDATE, "moreInfo");
		object.setAudit(audit);

	}

	@Override
	public void validate(final AuditRecord object) {

		if (!super.getBuffer().getErrors().hasErrors(AuditorAuditRecordCreateService.INITDATE) && !super.getBuffer().getErrors().hasErrors(AuditorAuditRecordCreateService.ENDDATE)) {
			if (!MomentHelper.isBefore(object.getInitDate(), object.getEndDate())) {
				super.state(false, AuditorAuditRecordCreateService.ENDDATE, "auditor.audit-record.form.error.end-before-start");
			} else {
				final double hours = MomentHelper.computeDuration(object.getInitDate(), object.getEndDate()).toHours();
				final double minutes = MomentHelper.computeDuration(object.getInitDate(), object.getEndDate()).toMinutes();
				if (hours > 1 || hours == 1 && minutes > 0)
					super.state(false, AuditorAuditRecordCreateService.ENDDATE, "auditor.audit-record.form.error.duration");
			}
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		Audit audit;
		int auditId;

		Tuple tuple;

		auditId = super.getRequest().getData(AuditorAuditRecordCreateService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(auditId);
		tuple = super.unbind(object, "subject", "assesment", "mark", AuditorAuditRecordCreateService.INITDATE, AuditorAuditRecordCreateService.ENDDATE, "moreInfo");
		tuple.put("tutorial", audit);
		tuple.put(AuditorAuditRecordCreateService.MASTERID, super.getRequest().getData(AuditorAuditRecordCreateService.MASTERID, int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
