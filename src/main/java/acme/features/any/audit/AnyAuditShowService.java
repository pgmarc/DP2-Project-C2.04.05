
package acme.features.any.audit;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyAuditShowService extends AbstractService<Any, Audit> {

	@Autowired
	protected AnyAuditRepository repository;


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
		status = audit != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	private String getMark(final Collection<String> markByAudit) {
		final List<String> topMark = new LinkedList<>();
		topMark.addAll(Arrays.asList("A+", "A", "B", "C", "D", "F", "F-"));
		for (final String mark : topMark)
			if (markByAudit.contains(mark))
				return mark;
		return "NR";
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);
		object.setMark(this.getMark(this.repository.getMarkByAudit(object.getId())));
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "id", "code", "conclusion", "strongPoints", "weakPoints", "mark", "draftMode");
		tuple.put("auditor", object.getAuditor().getUserAccount().getUsername());

		super.getResponse().setData(tuple);
	}

}
