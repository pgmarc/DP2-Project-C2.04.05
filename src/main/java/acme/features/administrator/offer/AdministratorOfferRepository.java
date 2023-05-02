
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("SELECT offer FROM Offer offer")
	Collection<Offer> findPublishedHandsOnCourses();

	@Query("SELECT offer FROM Offer offer WHERE offer.id = :offerId")
	Offer findOfferById(int offerId);
}
