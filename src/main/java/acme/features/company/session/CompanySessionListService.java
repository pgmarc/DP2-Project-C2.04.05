
package acme.features.company.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	private CompanySessionRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		int userAccountId;
		Company company;
		boolean isCompany;
		int practicumId;
		Practicum practicum;
		boolean practicumExists;
		boolean practicumBelongsToCompany = false;
		boolean status;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.repository.findCompanyByUserAccountId(userAccountId);
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		practicumExists = practicum != null;

		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);

		if (practicumExists)
			practicumBelongsToCompany = company.getId() == practicum.getCompany().getId();

		status = isCompany && practicumExists && practicumBelongsToCompany;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Collection<PracticumSession> sessions;
		int practicumId;

		practicumId = super.getRequest().getData("practicumId", int.class);
		sessions = this.repository.findSessionsByPracticumId(practicumId);

		super.getBuffer().setData(sessions);
	}

	@Override
	public void unbind(final PracticumSession session) {

		Tuple tuple;
		String icon;

		icon = session.isAddendum() ? "!" : "-";
		tuple = super.unbind(session, "title", "startingDate", "endingDate");
		tuple.put("addendum", icon);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		int practicumId, numberOfAddendum;
		Practicum practicum;
		boolean showCreate, showCreateAddendum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		numberOfAddendum = this.repository.countAddendumSessions(practicumId);

		showCreate = practicum.isDraftMode();
		showCreateAddendum = !practicum.isDraftMode() && numberOfAddendum == 0;

		super.getResponse().setGlobal("practicumId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("showCreateAddendum", showCreateAddendum);

	}
}
