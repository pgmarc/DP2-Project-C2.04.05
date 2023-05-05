
package acme.features.student.dashboard;

import java.util.List;
import java.util.stream.Collectors;

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

		final List<Double> workTimeActivities = this.repository.findDatesOfActivitiesByStudentId(student.getId()).stream().map(activity -> (double) activity.getWorkTime()).collect(Collectors.toList());
		final double MEDIA_WORKTIME = workTimeActivities.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		final double VARIANZA_WORKTIME = workTimeActivities.stream().mapToDouble(Double::doubleValue).map(d -> Math.pow(d - MEDIA_WORKTIME, 2)).average().orElse(0.0);

		final List<Double> learningTimesByCourses = this.repository.findLearningTimeCoursesByStudentId(student.getId());
		final double MEDIA_LEARNING_TIME = learningTimesByCourses.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		final double VARIANZA_LEARNING_TIME = learningTimesByCourses.stream().mapToDouble(Double::doubleValue).map(d -> Math.pow(d - MEDIA_LEARNING_TIME, 2)).average().orElse(0.0);

		numberOfTheoryActivities = this.repository.findNumberOfTheoryActivitiesByStudentId(student.getId());
		numberOfHandsOnActivities = this.repository.findNumberOfHandsOnActivitiesByStudentId(student.getId());
		numberOfBalancedActivities = this.repository.findNumberOfBalancedActivitiesByStudentId(student.getId());
		averageWorkbook = MEDIA_WORKTIME;
		maximumWorkbook = workTimeActivities.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		minimumWorkbook = workTimeActivities.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		deviationWorkbook = Math.sqrt(VARIANZA_WORKTIME);
		averageCourse = MEDIA_LEARNING_TIME;
		maximumCourse = learningTimesByCourses.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		minimumCourse = learningTimesByCourses.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		deviationCourse = Math.sqrt(VARIANZA_LEARNING_TIME);

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
