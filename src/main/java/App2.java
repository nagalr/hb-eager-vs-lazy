import com.example.data.HibernateUtil;
import com.example.domain.Course;
import com.example.domain.Instructor;
import com.example.domain.InstructorDetail;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by ronnen on 18-Jan-2021
 */


public class App2 {

    public static void main(String[] args) {

        // try-with-resources to close the session at the end
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // creates two instructor objects and a course object
            Instructor instructor =
                    new Instructor("John", "Doe", "john@gmail.com");

            System.out.println("instructor1: " + instructor);

            Instructor instructor2 =
                    new Instructor("Mark", "Jane", "mark@gmail.com");

            InstructorDetail instructorDetail =
                    new InstructorDetail("https://youtube.com/hello",
                            "Video Games");

            // creates bidi-link between the objects
            instructor.setInstructorDetail(instructorDetail);
            instructorDetail.setInstructor(instructor);

            Course course1 = new Course("Math");

            instructor.add(course1);

            // start a transaction
            session.beginTransaction();

            // save both objects in one statement since cascade type is All
            session.save(instructor);

            // save the second object that don't has association
            session.save(instructor2);

            session.save(course1);

            // will not delete the associated instructor
//            session.delete(course1);

            // commit the transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }

        // try-with-resources to close the session at the end
        // second try-block to test DB interactions
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            /*
             retrieve instructor objects from the DB
             create some courses and add them to the instructor
             save the courses
             start a transaction
            */
            Long theId = 1L;
            Long theId2 = 2L;
            Instructor inst = session.get(Instructor.class, theId);
            Instructor inst2 = session.get(Instructor.class, theId2);

            Course course1 = new Course("Physics");
            Course course2 = new Course("Biology");
            Course course3 = new Course("Chemistry");

            inst.add(course1);
            inst2.add(course2);
            inst2.add(course3);

            session.beginTransaction();

            session.save(course1);
            session.save(course2);
            session.save(course3);

            // commit the transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }

        // try-with-resources to close the session at the end
        // third try-block to test retrieval from the DB and display
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // retrieve instructor from the DB with id 1L
            Long theId = 1L;
            Instructor instructor = session.get(Instructor.class, theId);

            // display the retrieved instructor and its courses
            System.out.println("Found Instructor: " + instructor);

            // retrieves the instructor courses, display them
            List<Course> courses = instructor.getCourses();

            System.out.println("Instructor Courses: ");

            session.beginTransaction();

            for (Course item : courses) {
                System.out.println(item);
                session.delete(item);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }
    }
}

// Taken From: https://is.gd/CAOUGF