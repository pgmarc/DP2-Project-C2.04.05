
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	@Autowired
	private CompanyPracticumRepository repository;


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

		status = practicumExists && isCompany && practicumBelongsToCompany;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Practicum practicum;
		int practicumId;
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findPublishedHandsOnCourses();
		choices = SelectChoices.from(courses, "code", practicum.getCourse());

		tuple = super.unbind(practicum, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "draftMode");
		tuple.put("estimatedTotalTime", practicum.getEstimatedTotalTime() * 0.9);
		tuple.put("practicaPeriodLength", practicum.getPracticaPeriodLength() / 24);
		tuple.put("course", practicum.getCourse().getId());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
