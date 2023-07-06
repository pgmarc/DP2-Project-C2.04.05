
package acme.features.authenticated.provider;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Provider;

@Repository
public interface AuthenticatedProviderRepository extends AbstractRepository {

	@Query("select p from Provider p where p.userAccount.id = :id")
	Provider findOneProviderByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
