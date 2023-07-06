
package acme.features.authenticated.consumer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Consumer;

@Repository
public interface AuthenticatedConsumerRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select c from Consumer c where c.userAccount.id = :id")
	Consumer findOneConsumerByUserAccountId(int id);

}
