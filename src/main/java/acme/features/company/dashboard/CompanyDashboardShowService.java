
package acme.features.company.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.PracticumSession;
import acme.forms.CompanyDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int userAccountId;
		int companyId;
		Company company;
		CompanyDashboard dashboard;
		Double averageDurationOfPracticumSessions;
		Double standardDeviationOfPracticumSessionsDuration;
		Double minDurationPracticumSessions;
		Double maxDurationPracticumSessions;
		Double averageDurationOfPracticum;
		Double standardDeviationOfPracticumDuration;
		Double minDurationPracticum;
		Double maxDurationPracticum;
		int[] activePracticumByMonth;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		company = this.repository.findCompanyByUserAccountId(userAccountId);

		dashboard = new CompanyDashboard();
		companyId = company.getId();

		averageDurationOfPracticumSessions = this.repository.averageDurationOfPracticumSessions(companyId);
		standardDeviationOfPracticumSessionsDuration = this.repository.standardDeviationOfPracticumSessionsDuration(companyId);
		minDurationPracticumSessions = this.repository.minDurationPracticumSessions(companyId);
		maxDurationPracticumSessions = this.repository.maxDurationPracticumSessions(companyId);
		averageDurationOfPracticum = this.repository.averageDurationOfPracticum(companyId);
		standardDeviationOfPracticumDuration = this.repository.standardDeviationOfPracticumDuration(companyId);
		minDurationPracticum = this.repository.minDurationPracticum(companyId);
		maxDurationPracticum = this.repository.maxDurationPracticum(companyId);
		activePracticumByMonth = this.computeActivePracticum(companyId);

		dashboard.setSessionsPeriodLengthAveragePerPractica(averageDurationOfPracticumSessions);
		dashboard.setSessionsPeriodLengthDeviationPerPractica(standardDeviationOfPracticumSessionsDuration);
		dashboard.setSessionsPeriodLengthMinimumPerPractica(minDurationPracticumSessions);
		dashboard.setSessionsPeriodLengthMaximunPerPractica(maxDurationPracticumSessions);
		dashboard.setPracticaPeriodLengthAverage(averageDurationOfPracticum);
		dashboard.setPracticaPeriodLengthDeviation(standardDeviationOfPracticumDuration);
		dashboard.setPracticaPeriodLengthMinimun(minDurationPracticum);
		dashboard.setPracticaPeriodLengthMaximun(maxDurationPracticum);
		dashboard.setPracticaPerCourseLastYear(activePracticumByMonth);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final CompanyDashboard dashboard) {
		Tuple tuple;

		tuple = super.unbind(dashboard, //
			"sessionsPeriodLengthAveragePerPractica", "sessionsPeriodLengthDeviationPerPractica", //
			"sessionsPeriodLengthMinimumPerPractica", "sessionsPeriodLengthMaximunPerPractica", //
			"practicaPeriodLengthAverage", "practicaPeriodLengthDeviation", //
			"practicaPeriodLengthMinimun", "practicaPeriodLengthMaximun");

		tuple.put("activePracticumByMonth", dashboard.getPracticaPerCourseLastYear());

		super.getResponse().setData(tuple);
	}

	public int[] computeActivePracticum(final int companyId) {

		final int[] activePracticumByMonths = {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		};

		final List<Integer> practicumIds = this.repository.findPracticumIds(companyId);

		for (final int id : practicumIds) {
			final boolean[] activeMonths = this.getActiveMonthsOfPracticum(id);
			for (int i = 0; i < 12; i++)
				if (activeMonths[i])
					activePracticumByMonths[i] += 1;
		}

		return activePracticumByMonths;
	}

	private boolean[] getActiveMonthsOfPracticum(final int practicumId) {
		final Date startingDeadline = MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.YEARS);
		final Date endingDeadline = MomentHelper.getCurrentMoment();
		final Collection<PracticumSession> sessions = this.repository.findSessionsAndEdgeCases(practicumId, startingDeadline, endingDeadline);
		final boolean[] active = {
			false, false, false, false, false, false, false, false, false, false, false, false
		};

		for (final PracticumSession session : sessions) {
			Date startingDate = session.getStartingDate();
			Date endingDate = session.getEndingDate();
			Calendar calendarStartingDate;
			Calendar calendarEndingDate;
			int sourceMonth;
			int targetMonth;
			int startingDateYear;
			int endingDateYear;
			boolean sessionStartingAndEndingDateInDiferentYear;

			if (MomentHelper.isBefore(startingDate, startingDeadline))
				startingDate = startingDeadline;

			if (MomentHelper.isAfter(endingDate, endingDeadline))
				endingDate = endingDeadline;

			calendarStartingDate = Calendar.getInstance();
			calendarEndingDate = Calendar.getInstance();
			calendarStartingDate.setTime(startingDate);
			calendarEndingDate.setTime(endingDate);

			startingDateYear = calendarStartingDate.get(Calendar.YEAR);
			endingDateYear = calendarEndingDate.get(Calendar.YEAR);
			sessionStartingAndEndingDateInDiferentYear = startingDateYear != endingDateYear;

			sourceMonth = calendarStartingDate.get(Calendar.MONTH);
			targetMonth = calendarEndingDate.get(Calendar.MONTH);

			if (sessionStartingAndEndingDateInDiferentYear)
				targetMonth += 12;

			this.setActiveMonths(active, sourceMonth, targetMonth);
		}
		return active;
	}

	private void setActiveMonths(final boolean[] activeMonths, final int sourceMonth, final int targetMonth) {
		for (int currentMonth = sourceMonth; currentMonth <= targetMonth; currentMonth++) {
			int month;
			month = currentMonth % 12;
			activeMonths[month] = true;
		}
	}
}
