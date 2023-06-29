
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepPublishService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Peep peep;
		Date currentMoment;
		Principal principal;
		UserAccount account;

		String nick = "";
		currentMoment = MomentHelper.getCurrentMoment();
		principal = super.getRequest().getPrincipal();

		if (principal.isAuthenticated()) {
			account = this.repository.findUserAccountById(principal.getAccountId());
			nick = account.getIdentity().getFullName();
		}

		peep = new Peep();
		peep.setInstantiationMoment(currentMoment);
		peep.setNick(nick);

		super.getBuffer().setData(peep);
	}

	@Override
	public void bind(final Peep peep) {
		assert peep != null;

		super.bind(peep, "title", "nick", "message", "email", "moreInfo");
	}

	@Override
	public void validate(final Peep peep) {
		assert peep != null;
	}

	@Override
	public void perform(final Peep peep) {
		assert peep != null;

		this.repository.save(peep);
	}

	@Override
	public void unbind(final Peep peep) {
		assert peep != null;

		Tuple tuple;

		tuple = super.unbind(peep, "instantiationMoment", "title", "nick", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);

	}

}
