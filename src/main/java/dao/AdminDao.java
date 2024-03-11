package dao;

import models.Admin;
import models.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class AdminDao implements DAO<Admin> {

    public void save(Admin admin) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(admin);
        tx1.commit();
        session.close();
    }

    public void update(Admin admin) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(admin);
        tx1.commit();
        session.close();
    }

    public void delete(Admin admin) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(admin);
        tx1.commit();
        session.close();
    }

    public Admin findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Admin admin = session.get(Admin.class, id);
        session.close();
        return admin;
    }

    public Users findUserById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Users.class, id);
    }

    public List<Admin> findAll() {
        List<Admin> admins = (List<Admin>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Admin").list();
        return admins;
    }
}