package utils;

import models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    private static String configFileName = "hibernate.cfg.xml";

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Admin.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Info.class);
                configuration.addAnnotatedClass(FinIndicators.class);
                configuration.addAnnotatedClass(FinReports.class);
                configuration.addAnnotatedClass(Users.class);
                configuration.addAnnotatedClass(ProductsAndService.class);
                configuration.addAnnotatedClass(Employees.class);


                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure(configFileName);
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение! " + e);
            }
        }
        return sessionFactory;
    }
}