
package acme.features.any.audit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AnyAuditListService extends AbstractService<Any, Audit> {

	private static final String		COURSEID	= "courseId";
	@Autowired
	protected AnyAuditRepository	repository;


	@Override
	public void check() {

		final boolean status = super.getRequest().hasData(AnyAuditListService.COURSEID, int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Any.class) && this.repository.findOneCourseById(super.getRequest().getData(AnyAuditListService.COURSEID, int.class)) != null);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		int courseId;

		courseId = super.getRequest().getData(AnyAuditListService.COURSEID, int.class);
		objects = this.repository.findManyAuditsByCourseId(courseId);

		for (final Audit audit : objects)
			audit.setMark(this.getMark(this.repository.getMarkByAudit(audit.getId())));

		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("showCreate", !this.repository.findOneCourseById(super.getRequest().getData(AnyAuditListService.COURSEID, int.class)).isDraftMode() && super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setGlobal(AnyAuditListService.COURSEID, super.getRequest().getData(AnyAuditListService.COURSEID, int.class));
	}

	private String getMark(final Collection<String> markByAudit) {
		if (!markByAudit.isEmpty())
			return ((List<String>) markByAudit).get(0);
		return "NR";
	}

	@Override
	public void unbind(final Audit object) {
		if (object == null)
			throw new NullPointerException();

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "draftMode");
		super.getResponse().setGlobal("showCreate", !this.repository.findOneCourseById(super.getRequest().getData(AnyAuditListService.COURSEID, int.class)).isDraftMode() && super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setData(tuple);
	}
}
