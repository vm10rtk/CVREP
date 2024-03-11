package services;


import dao.*;


import models.*;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UsersService  {
    private UsersDAO usersDAO = new UsersDAO();



    public Users findEntity(int id) {
        Users entity = (Users)this.usersDAO.findById(id);
        return entity;
    }

    public void saveEntity(Users entity) {
        this.usersDAO.save(entity);
    }

    public void deleteEntity(Users entity) {
        this.usersDAO.delete(entity);
    }

    public void updateEntity(Users entity) {
        this.usersDAO.update(entity);
    }
    public Admin findAdminByUserId(int user_id) {return usersDAO.findAdminByUserId(user_id);}
    public Users findIDByLogin(String login){return usersDAO.findIDByLogin(login);}
    public Users findIDByLoginNPassword(String l, int p) {return usersDAO.findIDByLoginNPassword(l,p);}
    public List<Users> findAllEntities() {
        return this.usersDAO.findAll();
    }
    public boolean findIfUserExist(Users user){
        List<Users> users = (List<Users>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Users where id = " + user.getId()).list();
        if(users.isEmpty()) return false;
        return true;
    }

}