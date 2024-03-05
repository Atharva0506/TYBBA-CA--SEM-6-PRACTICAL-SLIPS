// Write a java Program in Hibernate to display Hello world message.

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HelloWorld {
    public static void main(String[] args) {
        // Create configuration object and configure Hibernate
        Configuration configuration = new Configuration().configure();
        // Create session factory
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // Open a session
        Session session = sessionFactory.openSession();
        // Display "Hello world" message
        System.out.println("Hello world");
        // Close the session and session factory
        session.close();
        sessionFactory.close();
    }
}
