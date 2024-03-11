package services;

import dao.AdminDao;
import models.Admin;
import models.Users;

import java.util.List;

public class AdminService {

    private AdminDao adminDao = new AdminDao();

    public AdminService() {
    }

    public Admin findAdmin(int id) {
        return adminDao.findById(id);
    }

    public void saveAdmin(Admin admin) {
        adminDao.save(admin);
    }

    public void deleteAdmin(Admin admin) {
        adminDao.delete(admin);
    }

    public void updateAdmin(Admin admin) {
        adminDao.update(admin);
    }

    public List<Admin> findAllAdmins() {
        return adminDao.findAll();
    }

    public Users findUserById(int id) {
        return adminDao.findUserById(id);
    }
}

