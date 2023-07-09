
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
public class CompanySessionCreateAddendumService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanySessionRepository		sessionRepository;

	@Autowired
	protected CompanyPracticumRepository	practicumRepository;


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
		int practicumId;
		int numberOfAddendums;
		Practicum practicum;
		boolean isCompany;
		boolean practicumExists;
		boolean practicumBelongsToCompany = false;
		final boolean addendumExists;
		boolean status;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.sessionRepository.findCompanyByUserAccountId(userAccountId);
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.sessionRepository.findPracticumById(practicumId);
		numberOfAddendums = this.sessionRepository.countAddendumSessions(practicumId);
		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);
		practicumExists = practicum != null;
		addendumExists = numberOfAddendums >= 1;

		if (practicumExists)
			practicumBelongsToCompany = company.getId() == practicum.getCompany().getId();

		status = practicumExists && isCompany && practicumBelongsToCompany && !practicum.isDraftMode() && !addendumExists;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Practicum practicum;
		int practicumId;
		PracticumSession session;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.sessionRepository.findPracticumById(practicumId);

		session = new PracticumSession();
		session.setPracticum(practicum);
		session.setAddendum(true);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final PracticumSession addendum) {

		super.bind(addendum, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo", "confirmation");
	}

	@Override
	public void validate(final PracticumSession addendum) {

		Date minimumDeadline;
		Date maximumDeadline;
		int practicumId;
		boolean isOneWeekAhead;
		boolean isOneWeekLong;
		boolean isSixMonthLongMax;
		boolean isStartingDateBeforeEndingDate;
		boolean isStartingDateUnderDeadline;
		boolean isEndingDateUnderDeadline;
		boolean isConfirmed;
		Double estimatedTotalTime;
		Double total;

		practicumId = addendum.getPracticum().getId();
		estimatedTotalTime = this.practicumRepository.findPracticumEstimatedTotalTimeByPracticumId(practicumId);

		if (!super.getBuffer().getErrors().hasErrors("startingDate") && !super.getBuffer().getErrors().hasErrors("endingDate")) {

			isStartingDateBeforeEndingDate = MomentHelper.isBefore(addendum.getStartingDate(), addendum.getEndingDate());
			super.state(isStartingDateBeforeEndingDate, "*", "company.session.form.error.endingDate-before-startingDate");

			minimumDeadline = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.WEEKS);
			isOneWeekAhead = MomentHelper.isAfterOrEqual(addendum.getStartingDate(), minimumDeadline);
			super.state(isOneWeekAhead, "*", "company.session.form.error.min-deadline");

			maximumDeadline = new Date(4133977199000L); // 2100/12/31 23:59:59 CEST
			isStartingDateUnderDeadline = MomentHelper.isBefore(addendum.getStartingDate(), maximumDeadline);
			isEndingDateUnderDeadline = MomentHelper.isBefore(addendum.getEndingDate(), maximumDeadline);
			super.state(isStartingDateUnderDeadline && isEndingDateUnderDeadline, "*", "company.session.form.error.max-deadline");

			isOneWeekLong = MomentHelper.isLongEnough(addendum.getStartingDate(), addendum.getEndingDate(), 1, ChronoUnit.WEEKS);
			super.state(isOneWeekLong, "*", "company.session.form.error.min-duration");

			isSixMonthLongMax = !MomentHelper.isLongEnough(addendum.getStartingDate(), addendum.getEndingDate(), 1000, ChronoUnit.HOURS);
			super.state(isSixMonthLongMax, "*", "company.session.form.error.max-duration");

			addendum.setDuration();
			total = estimatedTotalTime + addendum.getDuration();
			super.state(total <= 99999.99, "*", "company.session.form.error.reach-estimated-total-time-limit");

		}

		isConfirmed = super.getRequest().getData("confirmation", boolean.class);
		super.state(isConfirmed, "confirmation", "company.session.form.error.must-confirm-addendum");

	}

	@Override
	public void perform(final PracticumSession addendum) {

		int practicumId;
		Practicum practicum;
		List<PracticumSession> startingDates;
		List<PracticumSession> endingDates;
		Date startingDate;
		Date endingDate;
		double estimatedTotalTime;

		addendum.setDuration();
		this.sessionRepository.save(addendum);

		practicumId = super.getRequest().getData("practicumId", int.class);
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
	public void unbind(final PracticumSession addendum) {
		Tuple tuple;
		int practicumId;

		practicumId = super.getRequest().getData("practicumId", int.class);

		tuple = super.unbind(addendum, "title", "sessionAbstract", "startingDate", "endingDate", "moreInfo", "addendum");
		tuple.put("practicumId", practicumId);

		super.getResponse().setData(tuple);
	}
}
