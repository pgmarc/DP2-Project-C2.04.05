
package acme.features.student.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("SELECT cl.lecture FROM CourseLecture cl WHERE cl.course.id = :id")
	List<Lecture> findAllLecturesByCourseId(int id);

	@Query("SELECT l FROM Lecture l WHERE l.id = :id")
	Lecture findOneLectureById(int id);
}
