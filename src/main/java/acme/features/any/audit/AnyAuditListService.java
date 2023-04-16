
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AnyAuditListService extends AbstractService<Any, Audit> {

	@Autowired
	protected AnyAuditRepository repository;


	@Override
	public void check() {

		final boolean status = super.getRequest().hasData("courseId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Authenticated.class) && this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class)) != null);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findManyAuditsByCourseId(courseId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "draftMode");

		super.getResponse().setGlobal("showCreate", super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setGlobal("courseId", super.getRequest().getData("courseId", int.class));
		super.getResponse().setData(tuple);
	}
}
