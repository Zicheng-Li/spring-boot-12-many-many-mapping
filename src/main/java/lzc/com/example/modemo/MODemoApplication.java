package lzc.com.example.modemo;

import lzc.com.example.modemo.dao.AppDAO;
import lzc.com.example.modemo.entity.Course;
import lzc.com.example.modemo.entity.Instructor;
import lzc.com.example.modemo.entity.InstructorDetail;
import lzc.com.example.modemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MODemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MODemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {

//			createCourseAndReview(appDAO);
//			findCourseAndReview(appDAO);
			deleteCourseAndReview(appDAO);
		};
	}
	private void deleteCourseAndReview(AppDAO appDAO) {
		int theId=10;
		System.out.println("Deleting the course and review with id: " + theId);
		appDAO.deleteCourseById(theId); // this will course and reviews
		System.out.println("done!");
	}


	private void findCourseAndReview(AppDAO appDAO) {
		int theId = 10;
		Course course = appDAO.findCourseAndReviewById(theId);
		System.out.println("course: " + course);
		System.out.println("course reviews: " + course.getReviews());
		System.out.println("done!");
	}

	private void createCourseAndReview(AppDAO appDAO) {
		// create a course
		Course course = new Course("x++");
		course.addReview(new Review("love this one"));
		course.addReview(new Review("love this x++"));
		course.addReview(new Review("love this java"));
		course.addReview(new Review("love this c"));
		appDAO.save(course);
		System.out.println("saving the course");
		System.out.println(course);
		System.out.println("course reviews: " + course.getReviews());
		System.out.println("done!");
	}

	private void deleteCourse(AppDAO appDAO) {
		int theId=10;
		System.out.println("deleting course with id: " + theId);
		appDAO.deleteCourseById(theId);
		System.out.println("done!");
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("deleting instructor with id: " + theId);
		appDAO.deleteById(theId);
		System.out.println("done!");
	}

	private void UpdateCourse(AppDAO appDAO) {
		Course course = appDAO.getCourseById(10);
        System.out.println("finding the course");
        System.out.println(course);
        // update the course
        course.setTitle("react");
        // save the course
        System.out.println("Saving course: " + course);
        appDAO.updateCourse(course);
        System.out.println("done!");
	}

	private void UpdateInstructor(AppDAO appDAO) {
		Instructor instructor = appDAO.findById(1);
        System.out.println("finding the instructor");
        System.out.println(instructor);
        // update the instructor
        instructor.setLastName("tester");
        // save the instructor
        System.out.println("Saving instructor: " + instructor);
        appDAO.updateInstructor(instructor);
        System.out.println("done!");
	}

	private void findInstructorWithCourseJoinFetch(AppDAO appDAO) {
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(1);
        System.out.println("finding the instructor");
        System.out.println(instructor);

        // find courses for the instructor
        System.out.println("finding the course with the id" + instructor.getCourses());
        System.out.println("done!");
	}

	private void findCourseForInstructor(AppDAO appDAO) {
		Instructor instructor = appDAO.findById(1);
		System.out.println("finding the instructor");
		System.out.println(instructor);

		// find courses for the instructor
		System.out.println("finding the course with the id");
		List<Course> courses = appDAO.findCoursesByInstructorId(1);
		System.out.println(courses);
        System.out.println("done!");
	}

	private void findInstructorWithCourse(AppDAO appDAO) {
		Instructor instructor = appDAO.findById(1);
		System.out.println("finding the instructor");
        System.out.println(instructor);
		System.out.println("the course" + instructor.getCourses());
		System.out.println("done!");
	}

	private void createInstructorWithCourse(AppDAO appDAO) {
// create instructor detail
		Instructor tempInstructor = new Instructor("HHHH", "lplplplp", "hhhh@rbc.com");

		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.youtube.com", "code");

		// associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// create the courses
		Course tempCourse1 = new Course("C++");
		Course tempCourse2 = new Course("Java");

		// add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		// save the instructor
		// note: this will ALSO save the course because of CascadeType.ALL
		System.out.println("Saving instructor: " + tempInstructor);
		System.out.println("Saving the course " + tempInstructor.getCourses() );
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}
