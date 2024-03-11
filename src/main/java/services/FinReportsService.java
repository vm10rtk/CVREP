package services;


import dao.*;

import models.*;
import dao.FinReportsDAO;

import java.util.List;

public class FinReportsService  {
    private FinReportsDAO finReportsDAO = new FinReportsDAO();



    public FinReports findEntity(int id) {
        FinReports entity = (FinReports)this.finReportsDAO.findById(id);
        return entity;
    }

    public void saveEntity(FinReports entity) {
        this.finReportsDAO.save(entity);
    }

    public void deleteEntity(FinReports entity) {
        this.finReportsDAO.delete(entity);
    }

    public void updateEntity(FinReports entity) {
        this.finReportsDAO.update(entity);
    }

    public List<FinReports> findAllEntities() {
        return this.finReportsDAO.findAll();
    }
    public FinReports findAllFinReportsofCompany(int idcompany){return this.finReportsDAO.findAllFinReportsofCompany(idcompany);}

}