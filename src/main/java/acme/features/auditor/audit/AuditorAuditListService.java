
package acme.features.auditor.audit;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyAuditsByAuditorId(auditorId);

		for (final Audit audit : objects)
			if (audit.getMark() == null)
				audit.setMark(this.getMark(this.repository.getMarkByAudit(audit.getId())));

		super.getBuffer().setData(objects);
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
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "draftMode");
		if (!object.isDraftMode())
			tuple.put("draftMode", "auditor.audit.list.label.draftMode.off");
		else
			tuple.put("draftMode", "auditor.audit.list.label.draftMode.on");

		super.getResponse().setData(tuple);
	}
}
