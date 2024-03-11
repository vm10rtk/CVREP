package dao;

import models.Employees;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeesDAO implements DAO<Employees>{
    public EmployeesDAO() {
    }

    public void save(Employees obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(Employees obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(Employees obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public Employees findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Employees employee = (Employees) session.get(Employees.class, id);
        session.close();
        return employee;
    }
    public Employees findAllEmployeesofCmopany(int idcompany){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employees> employees = session.createQuery("From Employees where idcompany='" + idcompany + "'").list();
        return employees.get(0);
    }
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employees> employees = session.createQuery("From Employees").list();
        session.close();
        return employees;
    }
}