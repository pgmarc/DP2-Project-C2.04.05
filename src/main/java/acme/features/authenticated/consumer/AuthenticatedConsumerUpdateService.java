
package acme.features.authenticated.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Consumer;

@Service
public class AuthenticatedConsumerUpdateService extends AbstractService<Authenticated, Consumer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedConsumerRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Consumer object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneConsumerByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Consumer object) {
		assert object != null;

		super.bind(object, "company", "sector");
	}

	@Override
	public void validate(final Consumer object) {
		assert object != null;
	}

	@Override
	public void perform(final Consumer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Consumer object) {
		assert object != null;

		Tuple tuple;

		tuple = BinderHelper.unbind(object, "company", "sector");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
