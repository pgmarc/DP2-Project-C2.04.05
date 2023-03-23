
package acme.features.any.banner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	@Query("SELECT b FORM Banner b WHERE b.id = :id")
	Banner findOneBannerById(int id);
}
