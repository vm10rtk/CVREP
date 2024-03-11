package dao;

import dao.*;
import models.*;
//import Server.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;
import java.util.List;

import java.util.List;

public class ProductsAndServiceDAO implements DAO<ProductsAndService> {
//    public ProductsAndServiceDAO() {
//    }

    public void save(ProductsAndService obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(ProductsAndService obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(ProductsAndService obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public ProductsAndService findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ProductsAndService pands = (ProductsAndService) session.get(ProductsAndService.class, id);
        session.close();
        return pands;
    }
//    public ProductsAndService findById(String id) {
//        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        ProductsAndService pands = (ProductsAndService) session.get(ProductsAndService.class, id);
//        session.close();
//        return pands;
//    }
    public ProductsAndService findAllProductsAndServicesofCompany(int idcompany){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<ProductsAndService> pands = session.createQuery("From ProductsAndService where idcompany='" + idcompany + "'").list();
        return pands.get(0);
    }
    public List<ProductsAndService> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<ProductsAndService> pands = session.createQuery("From ProductsAndService").list();
        session.close();
        return pands;
    }
}