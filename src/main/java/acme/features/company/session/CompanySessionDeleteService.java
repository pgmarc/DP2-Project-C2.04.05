
package acme.features.company.session;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.features.company.practicum.CompanyPracticumRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionDeleteService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanySessionRepository		sessionRepository;

	@Autowired
	protected CompanyPracticumRepository	practicumRepository;


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
		Practicum practicum;
		boolean isCompany;
		boolean practicumSessionExists;
		boolean practicumBelongsToCompany = false;
		boolean status;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.sessionRepository.findCompanyByUserAccountId(userAccountId);
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicumSession = this.sessionRepository.findPracticumSessionById(practicumSessionId);
		practicum = practicumSession.getPracticum();
		practicumSessionExists = practicumSession != null;
		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);

		if (practicumSessionExists)
			practicumBelongsToCompany = company.getId() == practicum.getCompany().getId();

		status = practicumSessionExists && isCompany && practicumBelongsToCompany && practicum.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int practicumId;
		PracticumSession practicumSession;

		practicumId = super.getRequest().getData("id", int.class);
		practicumSession = this.sessionRepository.findPracticumSessionById(practicumId);

		super.getBuffer().setData(practicumSession);
	}

	@Override
	public void bind(final PracticumSession practicumSession) {
		super.bind(practicumSession, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo", "addendum");
	}

	@Override
	public void validate(final PracticumSession practicumSession) {

	}

	@Override
	public void perform(final PracticumSession practicumSession) {

		int practicumId;
		Practicum practicum;
		int numberOfSessions;
		double estimatedTotalTime = 0;
		Date startingDate = null;
		Date endingDate = null;

		boolean isPracticumEmpty;

		this.sessionRepository.delete(practicumSession);

		practicumId = practicumSession.getPracticum().getId();
		practicum = this.sessionRepository.findPracticumById(practicumId);
		numberOfSessions = this.practicumRepository.countPracticumSessionsByPracticumId(practicumId);
		isPracticumEmpty = numberOfSessions == 0;

		if (!isPracticumEmpty) {
			final List<PracticumSession> sessions = this.sessionRepository.findPracticumSessionsByPracticumIdSortedByStartingDate(practicumId);
			estimatedTotalTime = this.practicumRepository.computeEstimatedTotalTime(practicumId);
			startingDate = sessions.get(0).getStartingDate();
			endingDate = sessions.get(sessions.size() - 1).getEndingDate();
		}

		practicum.setEstimatedTotalTime(estimatedTotalTime);
		practicum.updatePracticaPeriodLength(startingDate, endingDate);
		this.practicumRepository.save(practicum);

	}

	@Override
	public void unbind(final PracticumSession practicumSession) {

		Tuple tuple;

		tuple = super.unbind(practicumSession, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo");
		tuple.put("draftMode", practicumSession.getPracticum().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
