
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("SELECT course FROM Course course WHERE course.id = :courseId and course.draftMode = FALSE")
	Course findCourseById(int courseId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.course.id = :courseId and practicum.draftMode = FALSE")
	Collection<Practicum> findPracticaByCourseId(int courseId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.id = :practicumId AND practicum.draftMode = FALSE")
	Practicum findPracticumById(int practicumId);

}
