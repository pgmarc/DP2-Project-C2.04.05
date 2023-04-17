
package acme.features.student.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		StudentDashboard dashboard;
		int numberOfTheoryActivities;
		int numberOfHandsOnActivities;
		int numberOfBalancedActivities;
		Double averageWorkbook;
		double maximumWorkbook;
		double minimumWorkbook;
		double deviationWorkbook;
		double averageCourse;
		double maximumCourse;
		double minimumCourse;
		double deviationCourse;

		Principal principal;
		int userAccountId;
		Student student;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		student = this.repository.findOneStudentByUserAccountId(userAccountId);

		numberOfTheoryActivities = this.repository.findNumberOfTheoryActivitiesByStudentId(student.getId());
		numberOfHandsOnActivities = this.repository.findNumberOfHandsOnActivitiesByStudentId(student.getId());
		numberOfBalancedActivities = this.repository.findNumberOfBalancedActivitiesByStudentId(student.getId());
		averageWorkbook = this.repository.findAverageWorkbookByStudentId(student.getId()) == null ? 0.0 : this.repository.findAverageWorkbookByStudentId(student.getId());
		maximumWorkbook = this.repository.findMaximumWorkbookByStudentId(student.getId()) == null ? 0.0 : this.repository.findMaximumWorkbookByStudentId(student.getId());
		minimumWorkbook = this.repository.findMinimumWorkbookByStudentId(student.getId()) == null ? 0.0 : this.repository.findMinimumWorkbookByStudentId(student.getId());
		deviationWorkbook = this.repository.findDeviationWorkbookByStudentId(student.getId()) == null ? 0.0 : this.repository.findDeviationWorkbookByStudentId(student.getId());
		averageCourse = this.repository.findAverageLearningTimeCourseByStudentId(student.getId()) == null ? 0.0 : this.repository.findAverageLearningTimeCourseByStudentId(student.getId());
		maximumCourse = this.repository.findMaximumLearningTimeCourseByStudentId(student.getId()) == null ? 0.0 : this.repository.findMaximumLearningTimeCourseByStudentId(student.getId());
		minimumCourse = this.repository.findMinimumLearningTimeCourseByStudentId(student.getId()) == null ? 0.0 : this.repository.findMinimumLearningTimeCourseByStudentId(student.getId());
		deviationCourse = this.repository.findDeviationLearningTimeCourseByStudentId(student.getId()) == null ? 0.0 : this.repository.findDeviationLearningTimeCourseByStudentId(student.getId());

		dashboard = new StudentDashboard();
		dashboard.setNumberOfTheoryActivities(numberOfTheoryActivities);
		dashboard.setNumberOfHandsOnActivities(numberOfHandsOnActivities);
		dashboard.setNumberOfBalancedActivities(numberOfBalancedActivities);
		dashboard.setAverageWorkbook(averageWorkbook);
		dashboard.setMaximumWorkbook(maximumWorkbook);
		dashboard.setMinimumWorkbook(minimumWorkbook);
		dashboard.setDeviationWorkbook(deviationWorkbook);
		dashboard.setAverageCourse(averageCourse);
		dashboard.setMaximumCourse(maximumCourse);
		dashboard.setMinimumCourse(minimumCourse);
		dashboard.setDeviationCourse(deviationCourse);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"numberOfTheoryActivities", "numberOfHandsOnActivities", // 
			"numberOfBalancedActivities", "averageWorkbook", //
			"maximumWorkbook", "minimumWorkbook", "deviationWorkbook", //
			"averageCourse", "maximumCourse", //
			"minimumCourse", "deviationCourse");

		super.getResponse().setData(tuple);
	}
}
