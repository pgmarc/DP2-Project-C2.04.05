
package acme.features.student.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode = 0")
	List<Course> findAllPublishCourses();

	@Query("SELECT e FROM Enrolment e WHERE e.course.id = :courseId and e.student.id = :studentId")
	Enrolment findOneEnrolmentByCourseIdAndStudentId(int courseId, int studentId);

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findOneStudentByUserAccountId(int id);
}
