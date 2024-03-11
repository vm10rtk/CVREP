package services;


import dao.CompanyDAO;

import models.*;

import java.util.Iterator;
import java.util.List;

public class CompanyService  {
   private CompanyDAO companyDAO = new CompanyDAO();

    public CompanyService() {
    }

    public Company findEntity(int id) {
        Company entity = (Company)this.companyDAO.findById(id);

        return entity;
    }

    public void saveEntity(Company entity) {
        this.companyDAO.save(entity);
    }

    public void deleteEntity(Company entity) {
        this.companyDAO.delete(entity);
    }

    public void updateEntity(Company entity) {
        this.companyDAO.update(entity);
    }

    public List<Company> findAllEntities() {
        return this.companyDAO.findAll();
    }
    public Company findAllEntities(String name) {
        Company entity = (Company)this.companyDAO.findByName(name);
        return entity;
    }
}