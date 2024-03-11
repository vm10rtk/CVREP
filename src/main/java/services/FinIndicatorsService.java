package services;


import dao.*;

import models.*;
import dao.FinIndicatorsDAO;
import dao.FinReportsDAO;

import java.util.List;

public class FinIndicatorsService{
    private FinIndicatorsDAO finIndicatorsDAO = new FinIndicatorsDAO();

    public FinIndicatorsService() {
    }

    public FinIndicators findEntity(int id) {
        FinIndicators entity = (FinIndicators)this.finIndicatorsDAO.findById(id);
        return entity;
    }

    public void saveEntity(FinIndicators entity) {
        this.finIndicatorsDAO.save(entity);
    }

    public void deleteEntity(FinIndicators entity) {
        this.finIndicatorsDAO.delete(entity);
    }

    public void updateEntity(FinIndicators entity) {
        this.finIndicatorsDAO.update(entity);
    }
    //public FinIndicators findIDByLogin(String name){return finIndicatorsDAO.findIDByLogin(name);}
    public List<FinIndicators> findAllEntities() {
        return this.finIndicatorsDAO.findAll();
    }
    public FinIndicators findAllFinINdicatorsofCompany(int idcompany){
        return this.finIndicatorsDAO.findAllFinIndicatorsCompany(idcompany);
    }
    public double getProfit(FinIndicators Find){
       return this.finIndicatorsDAO.getProfit(Find);
    }
    public double getLiquidity(FinIndicators Find){
        return this.finIndicatorsDAO.getLiquidity(Find);
    }
}