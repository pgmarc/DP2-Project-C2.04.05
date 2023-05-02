
package acme.features.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.offer.Offer;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int userAccountId, courseId;
		Company company;
		Practicum practicum;
		Course course;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		practicum = new Practicum();
		practicum.setStartingDate(null);
		practicum.setEndingDate(null);

		practicum.setCompany(company);
		practicum.setDraftMode(true);

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		practicum.setCourse(course);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Offer object) {

		super.bind(object, "code", "title", "practicumAbstract", "goals", "draftMode");
	}

	@Override
	public void validate(final Offer object) {
	}

	@Override
	public void perform(final Offer offer) {

		this.repository.save(offer);
	}

	@Override
	public void unbind(final Offer offer) {

		Tuple tuple;

		tuple = super.unbind(offer, "code", "title", "practicumAbstract", "goals", "draftMode");

		super.getResponse().setData(tuple);
	}
}
