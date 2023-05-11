
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.tutorial.id = :tutorialId")
	Collection<TutorialSession> findSessionsByTutorialId(int tutorialId);

	@Query("SELECT a FROM Assistant a WHERE a.id = :activeRoleId")
	Assistant findOneAssistantById(int activeRoleId);

	@Query("SELECT c FROM Course c WHERE c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("SELECT c FROM Course c WHERE c.draftMode = TRUE")
	Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.code = :code")
	Course findOneCourseByCode(String code);

}
