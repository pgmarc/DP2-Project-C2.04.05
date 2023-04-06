
package acme.features.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyAuthenthicatedAuditByCourseShowService extends AbstractService<Authenticated, Audit> {

	@Autowired
	protected AnyAuthenthicatedAuditByCourseRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class) && super.getRequest().hasData("courseid", int.class);

		super.getResponse().setChecked(status);

	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final int courseid = super.getRequest().getData("courseid", int.class);
		Audit audit;

		audit = this.repository.findAuditByAuditor(id, courseid);

		super.getBuffer().setData(audit);
	}

	@Override
	public void unbind(final Audit object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "course", "isPublished");
		super.getResponse().setData(tuple);
	}
}
