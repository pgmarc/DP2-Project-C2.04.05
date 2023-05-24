
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
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


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

		int userAccountId;
		Company company;
		Practicum practicum;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		company = this.repository.findCompanyByUserAccountId(userAccountId);
		practicum = new Practicum();
		practicum.setStartingDate(null);
		practicum.setEndingDate(null);

		practicum.setDraftMode(true);
		practicum.setEstimatedTotalTime(0);
		practicum.setPracticaPeriodLength(0);
		practicum.setCompany(company);
		practicum.setCourse(null);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum practicum) {

		int courseId;
		Course course;

		super.bind(practicum, "code", "title", "practicumAbstract", "goals");

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		practicum.setCourse(course);

	}

	@Override
	public void validate(final Practicum practicum) {

		Practicum practicumWithCode;
		boolean practicumDoesNotExists;
		boolean status;
		String code;

		code = practicum.getCode();
		practicumWithCode = this.repository.findPracticumByCode(code);

		practicumDoesNotExists = practicumWithCode == null;

		status = practicumDoesNotExists || practicum.getId() == practicumWithCode.getId();
		super.state(status, "code", "company.practicum.form.error.practicum-already-exists");
	}

	@Override
	public void perform(final Practicum practicum) {

		this.repository.save(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findPublishedHandsOnCourses();
		choices = SelectChoices.from(courses, "code", practicum.getCourse());

		tuple = super.unbind(practicum, "code", "title", "practicumAbstract", "goals", "draftMode", "estimatedTotalTime", "practicaPeriodLength", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
