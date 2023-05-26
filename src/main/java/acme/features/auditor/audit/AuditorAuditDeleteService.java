
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		if (object == null)
			throw new NullPointerException();

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
		object.setDraftMode(true);
	}

	@Override
	public void validate(final Audit object) {
		if (object == null)
			throw new NullPointerException();
	}

	@Override
	public void perform(final Audit object) {
		if (object == null)
			throw new NullPointerException();

		final Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findManyAuditRecordsByAuditId(object.getId());
		this.repository.deleteAll(auditRecords);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Audit object) {
		if (object == null)
			throw new NullPointerException();

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");

		super.getResponse().setData(tuple);
	}

}
