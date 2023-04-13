
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT e FROM Enrolment e JOIN e.course c where e.student.id = :id")
	List<Enrolment> findAllEnrolmentsByStudentId(int id);

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	List<Activity> findAllActivitiesByEnrolmentId(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);
}
