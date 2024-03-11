package dao;


import models.FinReports;
import models.ProductsAndService;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class FinReportsDAO implements DAO<FinReports> {
    public FinReportsDAO() {
    }

    public void save(FinReports obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(FinReports obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(FinReports obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public FinReports findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        FinReports report = (FinReports) session.get(FinReports.class, id);
        session.close();
        return report;
    }
    public FinReports findAllFinReportsofCompany(int idcompany){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<FinReports> pands = session.createQuery("From FinReports where idcompany='" + idcompany + "'").list();
        return pands.get(0);
    }
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<FinReports> reports = session.createQuery("From FinReports").list();
        session.close();
        return reports;
    }
}