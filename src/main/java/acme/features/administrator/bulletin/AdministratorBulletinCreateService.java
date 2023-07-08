
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Bulletin;
import acme.features.authenticated.bulletin.AuthenticatedBulletinRepository;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AuthenticatedBulletinRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Bulletin bulletin;

		Date currentMoment;

		currentMoment = MomentHelper.getCurrentMoment();

		bulletin = new Bulletin();
		bulletin.setInstantiationMoment(currentMoment);

		super.getBuffer().setData(bulletin);
	}

	@Override
	public void bind(final Bulletin bulletin) {
		assert bulletin != null;

		super.bind(bulletin, "title", "message", "critical", "moreInfo", "confirmation");
	}

	@Override
	public void validate(final Bulletin bulletin) {
		assert bulletin != null;

		boolean isConfirmed;

		isConfirmed = super.getRequest().getData("confirmation", boolean.class);
		super.state(isConfirmed, "confirmation", "administrator.bulletin.form.error.label.confirmation");
	}

	@Override
	public void perform(final Bulletin bulletin) {
		assert bulletin != null;

		this.repository.save(bulletin);
	}

	@Override
	public void unbind(final Bulletin bulletin) {
		Tuple tuple;

		tuple = super.unbind(bulletin, "instantiationMoment", "title", "message", "critical", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
