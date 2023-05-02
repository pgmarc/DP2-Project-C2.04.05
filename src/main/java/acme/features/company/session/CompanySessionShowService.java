
package acme.features.company.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionShowService extends AbstractService<Company, PracticumSession> {

	@Autowired
	private CompanySessionRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		int userAccountId, practicumSessionId;
		Company company;
		PracticumSession practicumSession;
		boolean isCompany;
		boolean practicumSessionExists;
		boolean practicumBelongsToCompany = false;
		boolean status;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.repository.findCompanyByUserAccountId(userAccountId);
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findPracticumSessionById(practicumSessionId);
		practicumSessionExists = practicumSession != null;
		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);

		if (practicumSessionExists)
			practicumBelongsToCompany = company.getId() == practicumSession.getPracticum().getCompany().getId();

		status = practicumSessionExists && isCompany && practicumBelongsToCompany;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int sessionId;
		PracticumSession practicumSession;

		sessionId = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findPracticumSessionById(sessionId);

		super.getBuffer().setData(practicumSession);
	}

	@Override
	public void unbind(final PracticumSession practicumSession) {

		Tuple tuple;

		tuple = super.unbind(practicumSession, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo", "addendum");
		tuple.put("draftMode", practicumSession.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
