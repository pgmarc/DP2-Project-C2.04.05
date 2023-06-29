
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.SystemCurrency;
import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("SELECT offer FROM Offer offer")
	Collection<Offer> findAllOffers();

	@Query("SELECT offer FROM Offer offer WHERE offer.id = :offerId")
	Offer findOfferById(int offerId);

	@Query("SELECT sc FROM SystemCurrency sc")
	SystemCurrency showSystemCurrency();
}
