
package acme.components;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offer.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b WHERE b.id = :id")
	Banner findOneBannerById(int id);

	@Query("SELECT b FROM Banner b")
	Collection<Banner> findAllBanners();

	@Query("SELECT b FROM Banner b WHERE :date BETWEEN b.displayStart AND b.displayFinish")
	Collection<Banner> findAllActiveBanners(Date date);

	default Banner findRandomBanner() {
		int size;
		List<Banner> list;

		list = (List<Banner>) this.findAllActiveBanners(MomentHelper.getCurrentMoment());
		size = list.size();
		if (size == 0)
			return null;
		else {
			Collections.shuffle(list);
			return list.get(0);
		}

	}

}
