
package acme.features.student.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

	@Query("select avg(e.workTime) from Enrolment e where e.student.id = :id and e.draftMode = 0")
	Double findAverageWorkbookByStudentId(int id);

	@Query("select max(e.workTime) from Enrolment e where e.student.id = :id and e.draftMode = 0")
	Double findMaximumWorkbookByStudentId(int id);

	@Query("select min(e.workTime) from Enrolment e where e.student.id = :id and e.draftMode = 0")
	Double findMinimumWorkbookByStudentId(int id);

	@Query("select stddev(e.workTime) from Enrolment e where e.student.id = :id and e.draftMode = 0")
	Double findDeviationWorkbookByStudentId(int id);

	@Query("select avg(cl.lecture.estimatedLearningTime) from CourseLecture cl join cl.course c where c.id in ( select e.course.id from Enrolment e where e.student.id = :id )")
	Double findAverageLearningTimeCourseByStudentId(int id);

	@Query("select max(cl.lecture.estimatedLearningTime) from CourseLecture cl join cl.course c where c.id in ( select e.course.id from Enrolment e where e.student.id = :id )")
	Double findMaximumLearningTimeCourseByStudentId(int id);

	@Query("select min(cl.lecture.estimatedLearningTime) from CourseLecture cl join cl.course c where c in ( select e.course from Enrolment e where e.student.id = :id )")
	Double findMinimumLearningTimeCourseByStudentId(int id);

	@Query("select stddev(cl.lecture.estimatedLearningTime) from CourseLecture cl join cl.course c where c in ( select e.course from Enrolment e where e.student.id = :id )")
	Double findDeviationLearningTimeCourseByStudentId(int id);

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);
}
