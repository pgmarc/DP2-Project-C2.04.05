
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.practicum.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumDeleteService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		int userAccountId;
		Company company;
		int practicumId;
		Practicum practicum;
		boolean isCompany;
		boolean practicumExists;
		boolean practicumBelongsToCompany = false;
		boolean status;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		company = this.repository.findCompanyByUserAccountId(userAccountId);
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		isCompany = super.getRequest().getPrincipal().hasRole(Company.class);
		practicumExists = practicum != null;

		if (practicumExists)
			practicumBelongsToCompany = company.getId() == practicum.getCompany().getId();

		status = practicumExists && isCompany && practicumBelongsToCompany && practicum.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum practicum) {
		super.bind(practicum, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");
	}

	@Override
	public void validate(final Practicum practicum) {

	}

	@Override
	public void perform(final Practicum practicum) {

		Collection<PracticumSession> sessions;

		sessions = this.repository.findSessionsByPracticumId(practicum.getId());

		this.repository.deleteAll(sessions);
		this.repository.delete(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {

		Tuple tuple;

		tuple = super.unbind(practicum, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");

		super.getResponse().setData(tuple);
	}

}
