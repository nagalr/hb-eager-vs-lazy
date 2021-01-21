import com.example.data.HibernateUtil;
import com.example.domain.Course;
import com.example.domain.Instructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Created by ronnen on 18-Jan-2021
 */


public class App2 {

    public static void main(String[] args) {

        // try-with-resources to close the session at the end
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // start a transaction
            session.beginTransaction();

            // creates two instructor objects and a course object
            Instructor instructor =
                    new Instructor("John", "Doe", "john@gmail.com");

//            Instructor instructor2 =
//                    new Instructor("Mark", "Jane", "mark@gmail.com");

//            InstructorDetail instructorDetail =
//                    new InstructorDetail("https://youtube.com/hello",
//                            "Video Games");

            // creates bidi-link between the objects
//            instructor.setInstructorDetail(instructorDetail);
//            instructorDetail.setInstructor(instructor);

            Course course1 = new Course("Math");

            instructor.add(course1);

            session.save(instructor);

            // save the second object that don't has association
//            session.save(instructor2);

            session.save(course1);

            // commit the transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }

        // try-with-resources to close the session at the end
        // testing retrieval from the DB, Eager vs Lazy
        try  {

            Session session2 = HibernateUtil.getSessionFactory().openSession();

            // start a transaction
            session2.beginTransaction();

            Long theId = 1L;

            // option1 using HQL
            Query<Instructor> query =
                    session2.createQuery("select i from Instructor i " +
                            "join fetch i.courses " +
                            "where i.id=:theInstructorId", Instructor.class);

            query.setParameter("theInstructorId", theId);

            Instructor instructor = query.getSingleResult();

            System.out.println("***** Query-Outcome: " + instructor);

            // option2, the same outcome, no HQL
            // the retrieval part, testing Eager vs Lazy
//            Instructor inst = session2.get(Instructor.class, 1L);

//            List<Course> courses = inst.getCourses();

            // executing this line will load the courses (LAZY)
//            System.out.println("***** Courses: " + courses);

            // commit the transaction
            session2.getTransaction().commit();

            session2.close();


        } catch (Exception e) {
            System.out.println("[ERROR] error while opening the session: " + e);
        }
    }
}

// Taken From: https://is.gd/CAOUGF