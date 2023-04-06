
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Audit object;
		object = new Audit();

		super.getBuffer().setData(object);
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
	}

	@Override
	public void perform(final Audit object) {
		if (object == null)
			throw new NullPointerException(this.ERROR_MSG);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
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
