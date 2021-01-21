import com.example.data.HibernateUtil;
import com.example.domain.Instructor;
import com.example.domain.InstructorDetail;
import org.hibernate.Session;

/**
 * Created by ronnen on 18-Jan-2021
 */


public class App {

    public static void main(String[] args) {


        try  {
            // define the session for the two students creation
            Session session = HibernateUtil.getSessionFactory().openSession();

            // creates two objects
            Instructor instructor =
                    new Instructor("John", "Doe", "john@gmail.com");

            InstructorDetail instructorDetail =
                    new InstructorDetail("myYouTube", "Golf");

            // associate the objects
            instructor.setInstructorDetail(instructorDetail);

            // start a transaction
            session.beginTransaction();

            // save both objects in one statement since cascade type is All
            session.save(instructor);

            // commit the transaction
            session.getTransaction().commit();

            // close the session that read a student from the DB
            session.close();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }
    }
}

// Taken From: https://is.gd/CAOUGF