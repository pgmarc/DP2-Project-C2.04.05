
package acme.features.authenticated.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfferRepository extends AbstractRepository {

	@Query("SELECT offer FROM Offer offer WHERE :date BETWEEN offer.startingDate AND offer.endingDate")
	Collection<Offer> findAvailableOffers(Date date);

	@Query("SELECT offer FROM Offer offer WHERE offer.id = :offerId AND :date BETWEEN offer.startingDate AND offer.endingDate")
	Offer findOfferById(int offerId, Date date);

}
