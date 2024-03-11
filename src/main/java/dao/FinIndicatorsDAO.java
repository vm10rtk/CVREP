package dao;

import dao.*;
import models.FinIndicators;
import models.Info;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FinIndicatorsDAO implements DAO<FinIndicators>{
    public FinIndicatorsDAO() {
    }

    public void save(FinIndicators obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    public void update(FinIndicators obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    public void delete(FinIndicators obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    public FinIndicators findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        FinIndicators indicator = (FinIndicators) session.get(FinIndicators.class, id);
        session.close();
        return indicator;
    }
    public FinIndicators findAllFinIndicatorsCompany(int idcompany){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<FinIndicators> info = session.createQuery("From FinIndicators where idcompany='" + idcompany + "'").list();
        return info.get(0);
    }
    public double getProfit(FinIndicators Find){
       double income =  Find.getInfoProfitability();
       double exp = Find.getInfoLiquidity();
       double profit = income-exp;
       return profit;
    }
    public double getLiquidity(FinIndicators Find){
        double income =  Find.getInfoProfitability();
        double exp = Find.getInfoLiquidity();
        double profit = income-exp;
        double liqindex = profit/income;
        return liqindex;
    }
    public List findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<FinIndicators> indicators = session.createQuery("From FinIndicators").list();
        session.close();
        return indicators;
    }
}