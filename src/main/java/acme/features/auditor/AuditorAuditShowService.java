
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class) && super.getRequest().hasData("code", String.class);

		super.getResponse().setChecked(status);

	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final String code = super.getRequest().getData("code", String.class);
		Audit audit;

		audit = this.repository.findAuditByAuditor(id, code);

		super.getBuffer().setData(audit);
	}

	@Override
	public void unbind(final Audit object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "course", "isPublished");
		super.getResponse().setData(tuple);
	}
}
