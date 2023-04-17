
package acme.features.lecturer.courselecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.CourseLecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select cl from CourseLecture cl where cl.course.lecturer.userAccount.id = :id and cl.lecture.lecturer.userAccount.id = :id and cl.course.draftMode = true")
	public List<CourseLecture> getCourseLecturesFromLecturer(int id);

	@Query("select cl from CourseLecture cl where cl.id = :id")
	public CourseLecture getCourseLectureById(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId and cl.lecture.id = :lectureId")
	public List<CourseLecture> getCourseLecturesByIds(int courseId, int lectureId);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	public List<CourseLecture> getRelationsFromCourseId(int id);
}
