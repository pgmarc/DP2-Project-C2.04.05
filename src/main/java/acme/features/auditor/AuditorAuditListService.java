
package acme.features.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		Collection<Audit> audits;

		audits = this.repository.findAuditByAuditor(id);

		super.getBuffer().setData(audits);
	}

	@Override
	public void unbind(final Audit object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "course", "isPublished");

		super.getResponse().setData(tuple);
	}
}
