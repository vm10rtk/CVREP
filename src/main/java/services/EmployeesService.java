package services;


import dao.EmployeesDAO;

import models.*;

import java.util.List;

public class EmployeesService  {
    private EmployeesDAO employeesDAO = new EmployeesDAO();

    public EmployeesService() {
    }

    public Employees findEntity(int id) {
        Employees entity = (Employees)this.employeesDAO.findById(id);
        return entity;
    }

    public void saveEntity(Employees entity) {
        this.employeesDAO.save(entity);
    }

    public void deleteEntity(Employees entity) {
        this.employeesDAO.delete(entity);
    }

    public void updateEntity(Employees entity) {
        this.employeesDAO.update(entity);
    }
    public Employees findAllEmployeesofCompany(int idcompany){return this.employeesDAO.findAllEmployeesofCmopany(idcompany);}
    public List<Employees> findAllEntities() {
        return this.employeesDAO.findAll();
    }
}