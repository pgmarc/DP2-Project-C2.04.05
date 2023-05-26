
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

	private static final String			DRAFTMODE	= "draftMode";

	@Autowired
	protected AuditorAuditRepository	repository;


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
		if (!markByAudit.isEmpty())
			return ((List<String>) markByAudit).get(0);
		return "NR";
	}

	@Override
	public void unbind(final Audit object) {
		if (object == null)
			throw new NullPointerException();

		Tuple tuple;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", AuditorAuditListService.DRAFTMODE);
		if (!object.isDraftMode()) {
			if (super.getRequest().getLocale().getLanguage().equals("es"))
				tuple.put(AuditorAuditListService.DRAFTMODE, "No es Borrador");
			else
				tuple.put(AuditorAuditListService.DRAFTMODE, "Not Draft");
		} else if (super.getRequest().getLocale().getLanguage().equals("es"))
			tuple.put(AuditorAuditListService.DRAFTMODE, "Borrador");
		else
			tuple.put(AuditorAuditListService.DRAFTMODE, "Draft");

		super.getResponse().setData(tuple);
	}
}
