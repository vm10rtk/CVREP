package dao;


import models.Company;
import models.Employees;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDAO implements DAO<Company>  {
    public CompanyDAO() {
    }

    public void save(Company obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(Company obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(Company obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public Company findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Company company = (Company) session.get(Company.class, id);
        session.close();
        return company;
    }
    public Company findByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Company company = (Company) session.get(Company.class, name);
        session.close();
        return company;
    }

    public List<Company> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Company> companies = session.createQuery("From Company").list();
        session.close();
        return companies;
    }

}