
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	private final String				ERROR_MSG	= "Unexpected error, undefined audit";

	@Autowired
	protected AuditorAuditRepository	repository;


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);

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
	public void bind(final Audit object) {
		if (object == null)
			throw new NullPointerException(this.ERROR_MSG);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "course", "auditor");
	}

	@Override
	public void validate(final Audit object) {
		if (object == null)
			throw new NullPointerException(this.ERROR_MSG);
		super.state(!object.isPublished(), "*", "auditor.audit.delete.published");
	}

	@Override
	public void perform(final Audit object) {
		if (object == null)
			throw new NullPointerException(this.ERROR_MSG);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Audit object) {
		if (object == null)
			throw new NullPointerException(this.ERROR_MSG);

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "course", "auditor");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
