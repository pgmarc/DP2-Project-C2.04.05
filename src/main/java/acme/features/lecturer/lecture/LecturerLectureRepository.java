
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.CourseLecture;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	public Lecture getLectureById(int id);

	@Query("select l from Lecture l where l.lecturer.userAccount.id = :id")
	public List<Lecture> getAllLecturesFromLecturer(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	public List<Lecture> getLecturesFromCourse(int id);

	@Query("select l from Lecturer l where l.id = :id")
	public Lecturer getLecturerById(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	public Lecturer getLecturerByAccountId(int id);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :id")
	public List<CourseLecture> getRelationsFromLectureId(int id);
}
