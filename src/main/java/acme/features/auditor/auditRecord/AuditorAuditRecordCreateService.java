
package acme.features.auditor.auditRecord;

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

	@Autowired
	protected AuditorAuditRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		Audit audit;
		int auditId;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		object = new AuditRecord();
		object.setAudit(audit);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		Audit audit;
		int auditId;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);

		super.bind(object, "subject", "assesment", "mark", "initDate", "endDate", "moreInfo");
		object.setAudit(audit);

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			if (!MomentHelper.isBefore(object.getInitDate(), object.getEndDate()))
				super.state(false, "endDate", "auditor.audit-record.form.error.end-before-start");
			else {
				final int hours = (int) MomentHelper.computeDuration(object.getInitDate(), object.getEndDate()).toHours();
				if (hours > 1)
					super.state(false, "endDate", "auditor.audit-record.form.error.duration");
			}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Audit audit;
		int auditId;

		Tuple tuple;

		auditId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		tuple = super.unbind(object, "subject", "assesment", "mark", "initDate", "endDate", "moreInfo");
		tuple.put("tutorial", audit);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
