package services;


import dao.*;

import models.*;

import java.util.List;

public class InfoService  {
    private InfoDAO infoDAO = new InfoDAO();



    public Info findEntity(int id) {
        Info entity = (Info)this.infoDAO.findById(id);
        return entity;
    }

    public void saveEntity(Info entity) {
        this.infoDAO.save(entity);
    }

    public void deleteEntity(Info entity) {
        this.infoDAO.delete(entity);
    }

    public void updateEntity(Info entity) {
        this.infoDAO.update(entity);
    }
    public Info findAllInfoCompany(int idcompany){return this.infoDAO.findAllInfoCompany(idcompany);}

    public List<Info> findAllEntities() {
        return this.infoDAO.findAll();
    }
}