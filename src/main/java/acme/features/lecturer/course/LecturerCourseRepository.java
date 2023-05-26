
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	public Course getCourseById(int id);

	@Query("select c from Course c where c.lecturer.userAccount.id = :id")
	public List<Course> getCoursesFromLecturer(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	public List<Lecture> getLecturesFromCourse(int id);

	@Query("select cl.course from CourseLecture cl where cl.lecture.id = :id")
	public List<Course> getCoursesFromLecture(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	public Lecturer getLecturerByAccountId(int id);

	@Query("select c from Course c")
	public List<Course> getAllCourses();

	@Query("select c from Course c where c.id != :id")
	public List<Course> getAllOtherCourses(int id);

	@Query("select sc.supportedCurrencies from SystemCurrency sc")
	public String getSupportedCurrencies();

}
