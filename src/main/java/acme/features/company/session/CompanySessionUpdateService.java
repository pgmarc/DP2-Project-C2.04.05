
package acme.features.company.session;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.features.company.practicum.CompanyPracticumRepository;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionUpdateService extends AbstractService<Company, PracticumSession> {

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

		int userAccountId;
		int practicumSessionId;
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

		int practicumSessionId;
		PracticumSession practicumSession;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicumSession = this.sessionRepository.findPracticumSessionById(practicumSessionId);

		super.getBuffer().setData(practicumSession);
	}

	@Override
	public void bind(final PracticumSession practicumSession) {

		super.bind(practicumSession, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo");
	}

	@Override
	public void validate(final PracticumSession session) {

		Date minimumDeadline;
		Date maximumDeadline;
		Date baseMoment;
		int practicumId;
		boolean isOneWeekAhead;
		boolean isOneWeekLong;
		boolean isSixMonthLongMax;
		boolean isStartingDateBeforeEndingDate;
		boolean isStartingDateUnderDeadline;
		boolean isEndingDateUnderDeadline;
		Double estimatedTotalTime;
		Double total;

		baseMoment = MomentHelper.getBaseMoment();
		practicumId = session.getPracticum().getId();
		estimatedTotalTime = this.practicumRepository.findPracticumEstimatedTotalTimeByPracticumId(practicumId);

		if (!super.getBuffer().getErrors().hasErrors("startingDate") && !super.getBuffer().getErrors().hasErrors("endingDate")) {

			isStartingDateBeforeEndingDate = MomentHelper.isBefore(session.getStartingDate(), session.getEndingDate());
			super.state(isStartingDateBeforeEndingDate, "*", "company.session.form.error.endingDate-before-startingDate");

			minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			isOneWeekAhead = MomentHelper.isAfter(session.getStartingDate(), minimumDeadline);
			super.state(isOneWeekAhead, "*", "company.session.form.error.min-deadline");

			maximumDeadline = MomentHelper.deltaFromMoment(baseMoment, 365, ChronoUnit.DAYS);
			isStartingDateUnderDeadline = MomentHelper.isBefore(session.getStartingDate(), maximumDeadline);
			isEndingDateUnderDeadline = MomentHelper.isBeforeOrEqual(session.getEndingDate(), maximumDeadline);
			super.state(isStartingDateUnderDeadline && isEndingDateUnderDeadline, "*", "company.session.form.error.max-deadline");

			isOneWeekLong = MomentHelper.isLongEnough(session.getStartingDate(), session.getEndingDate(), 7, ChronoUnit.DAYS);
			super.state(isOneWeekLong, "*", "company.session.form.error.min-duration");

			isSixMonthLongMax = !MomentHelper.isLongEnough(session.getStartingDate(), session.getEndingDate(), 180, ChronoUnit.DAYS);
			super.state(isSixMonthLongMax, "*", "company.session.form.error.max-duration");

			session.setDuration();
			total = estimatedTotalTime + session.getDuration();
			super.state(total <= 9999.99, "*", "company.session.form.error.reach-estimated-total-time-limit");

		}
	}

	@Override
	public void perform(final PracticumSession practicumSession) {

		int practicumId;
		Practicum practicum;
		List<PracticumSession> startingDates;
		List<PracticumSession> endingDates;
		Date startingDate;
		Date endingDate;
		double estimatedTotalTime;

		practicumSession.setDuration();
		this.sessionRepository.save(practicumSession);

		practicumId = practicumSession.getPracticum().getId();
		practicum = this.sessionRepository.findPracticumById(practicumId);
		startingDates = this.sessionRepository.findPracticumSessionsByPracticumIdSortedByStartingDate(practicumId);
		endingDates = this.sessionRepository.findPracticumSessionsByPracticumIdSortedByEndingDate(practicumId);
		startingDate = startingDates.get(0).getStartingDate();
		endingDate = endingDates.get(0).getEndingDate();

		estimatedTotalTime = this.practicumRepository.computeEstimatedTotalTime(practicumId);
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
