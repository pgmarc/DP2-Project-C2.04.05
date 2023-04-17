
package acme.features.student.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	Activity findOneActivityById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	List<Activity> findAllActivitiesByEnrolmentId(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);
}
