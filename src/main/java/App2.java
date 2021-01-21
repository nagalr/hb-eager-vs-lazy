import com.example.data.HibernateUtil;
import com.example.domain.Course;
import com.example.domain.Instructor;
import com.example.domain.InstructorDetail;
import org.hibernate.Session;

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

            // commit the transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }
    }
}

// Taken From: https://is.gd/CAOUGF