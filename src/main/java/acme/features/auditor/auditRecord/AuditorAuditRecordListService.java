
package acme.features.auditor.auditRecord;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

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
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && super.getRequest().getPrincipal().getUsername().equals(audit.getAuditor().getUserAccount().getUsername());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecord> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyAuditRecordsByAuditId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Tuple tuple;
		final Duration duration;

		tuple = super.unbind(object, "subject", "assesment", "mark", "initDate", "endDate", "moreInfo");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;

		int masterId;
		final Audit audit;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		showCreate = audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setGlobal("masterId", masterId);
	}

}
