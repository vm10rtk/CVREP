package dao;

import dao.*;
import models.*;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAO implements DAO<Users>{
    public UsersDAO() {
    }

    public void save(Users obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(Users obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(Users obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public Users findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Users user = (Users) session.get(Users.class, id);
        session.close();
        return user;
    }

    public Admin findAdminByUserId(int user_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Admin> admin = (List<Admin>) session.createQuery("From Admin where user_id = " + user_id).list();
        session.close();
        if (admin.isEmpty()) return null;
        return admin.get(0);
    }
    public Users findIDByLogin(String login) {
        List<Users> users = (List<Users>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Users where login = '" + login + "'").list();
        if (users.isEmpty()) return null;
        return users.get(0);
    }
    public Users findIDByLoginNPassword(String login, int password) {
        List<Users> users = (List<Users>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Users where login = '" + login + "' and password = " + password).list();
        if (users.isEmpty()) return null;
        return users.get(0);
    }
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Users> users = session.createQuery("From Users").list();
        session.close();
        return users;
    }
}