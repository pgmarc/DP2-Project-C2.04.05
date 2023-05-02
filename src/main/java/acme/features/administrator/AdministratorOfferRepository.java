
package acme.features.administrator;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("SELECT company FROM Company company WHERE company.userAccount.id = :id")
	Company findOneCompanyByUserAccountId(int id);

	@Query("SELECT course FROM Course course WHERE course.nature = 1 AND course.draftMode = FALSE")
	Collection<Course> findPublishedHandsOnCourses();

	@Query("SELECT course FROM Course course WHERE course.id = :courseId")
	Course findCourseById(int courseId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("SELECT practicum FROM Practicum practicum WHERE practicum.id = :practicumId")
	Practicum findPracticumById(int practicumId);

}
