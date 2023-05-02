
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT company FROM Company company WHERE company.userAccount.id = :id")
	Company findCompanyByUserAccountId(int id);

	@Query("SELECT course FROM Course course WHERE course.nature = acme.enumerates.Nature.HANDS_ON AND course.draftMode = FALSE")
	Collection<Course> findPublishedHandsOnCourses();

	@Query("SELECT course FROM Course course WHERE course.id = :courseId")
	Course findCourseById(int courseId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.id = :practicumId")
	Practicum findPracticumById(int practicumId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.code LIKE :code")
	Practicum findPracticumByCode(String code);

	@Query("SELECT session FROM PracticumSession session WHERE session.practicum.id = :practicumId")
	Collection<PracticumSession> findSessionsByPracticumId(int practicumId);

	@Query("SELECT count(session) FROM PracticumSession session WHERE session.practicum.id = :practicumId AND session.addendum = FALSE")
	Integer countPracticumSessionsByPracticumId(int practicumId);

	@Query("SELECT sum(session.duration) FROM PracticumSession session WHERE session.practicum.id = :practicumId")
	Double computeEstimatedTotalTime(int practicumId);
}
