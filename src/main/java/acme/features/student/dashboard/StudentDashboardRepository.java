
package acme.features.student.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Activity;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Activity a where a.type = 0 and a.enrolment.student.id = :id")
	int findNumberOfTheoryActivitiesByStudentId(int id);

	@Query("select count(a) from Activity a where a.type = 1 and a.enrolment.student.id = :id")
	int findNumberOfHandsOnActivitiesByStudentId(int id);

	@Query("select count(a) from Activity a where a.type = 2 and a.enrolment.student.id = :id")
	int findNumberOfBalancedActivitiesByStudentId(int id);

	@Query("select a from Activity a where a.enrolment.student.id=:id")
	List<Activity> findDatesOfActivitiesByStudentId(int id);

	@Query("select sum(cl.lecture.estimatedLearningTime) from CourseLecture cl join cl.course c where c.id in ( select e.course.id from Enrolment e where e.student.id = :id ) group by c")
	List<Double> findLearningTimeCoursesByStudentId(int id);

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);
}
