package lzc.com.example.modemo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lzc.com.example.modemo.entity.Course;
import lzc.com.example.modemo.entity.Instructor;
import lzc.com.example.modemo.entity.InstructorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    // define fields for entity-manager
    private EntityManager entityManager;

    // inject entity-manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    // create constructor

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findById(int theId) {
        return entityManager.find(Instructor.class ,theId);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        // retrieve instructor by id
        Instructor tempinstructor = entityManager.find(Instructor.class ,theId);

        // get the courses for the instructor
        List<Course> courses = tempinstructor.getCourses();
        // delete the associated object reference
        // break bidirectional link
        for (Course course : courses) {
            course.setInstructor(null);
        }
        // delete the instructor
        if(tempinstructor != null) {
            entityManager.remove(tempinstructor);
        }
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class ,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        // retrieve instructor by id
        InstructorDetail tempinstructorDetail = entityManager.find(InstructorDetail.class ,theId);

        // remove the associated object reference
        // break bidirectional link
        if(tempinstructorDetail != null) {
            tempinstructorDetail.getInstructor().setInstructorDetail(null);
        }

        // delete the instructor
        if(tempinstructorDetail != null) {
            entityManager.remove(tempinstructorDetail);
        }

    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        // create a query
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id = :data" ,Course.class);
        query.setParameter("data" ,theId);

        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        // create a query
        TypedQuery<Instructor> query = entityManager.createQuery("select i from Instructor i " + "JOIN FETCH i.courses "+ "JOIN FETCH i.instructorDetail "+ "where i.id = :data" ,Instructor.class);
        query.setParameter("data" ,theId);

        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course getCourseById(int theId) {
       return entityManager.find(Course.class, theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        Course theCourse = entityManager.find(Course.class, theId);
        entityManager.remove(theCourse);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course); // it will also save the comments
    }

    @Override
    public Course findCourseAndReviewById(int theId) {
        // create a query
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c " + "JOIN FETCH c.reviews " + "where c.id = :data" ,Course.class);
        query.setParameter("data" ,theId);
        // execute query
        Course course = query.getSingleResult();
        return course;
    }

    @Override
    public Course findCourseAndStudentById(int theId) {
        // create a query
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c " + "JOIN FETCH c.students " + "where c.id = :data" ,Course.class);
        query.setParameter("data" ,theId);
        // execute query
        Course course = query.getSingleResult();
        return course;
    }

}



