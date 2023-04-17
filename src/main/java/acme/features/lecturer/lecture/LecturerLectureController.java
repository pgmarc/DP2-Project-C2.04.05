
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureListAllService			listAllService;

	@Autowired
	protected LecturerLectureListFromCourseService	listFromCourseService;

	@Autowired
	protected LecturerLectureShowService			showService;

	@Autowired
	protected LecturerLectureUpdateService			updateService;

	@Autowired
	protected LecturerLectureCreateService			createService;

	@Autowired
	protected LecturerLectureDeleteService			deleteService;

	@Autowired
	protected LecturerLecturePublishService			publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addCustomCommand("list-from-course", "list", this.listFromCourseService);
	}
}
