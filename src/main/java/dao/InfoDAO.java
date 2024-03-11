package dao;


import models.Info;
import models.ProductsAndService;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;



public class InfoDAO  implements DAO<Info>{
//    public InfoDAO() {
//    }

    public void save(Info obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(Info obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(Info obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public Info findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Info info = (Info) session.get(Info.class, id);
        session.close();
        return info;
    }
    public Info findAllInfoCompany(int idcompany){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Info> info = session.createQuery("From Info where idcompany='" + idcompany + "'").list();
        return info.get(0);
    }
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Info> info = session.createQuery("From Info").list();
        session.close();
        return info;
    }
}