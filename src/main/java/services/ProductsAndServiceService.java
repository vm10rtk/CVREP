package services;


import dao.ProductsAndServiceDAO;
//import Server.Interfaces.DAO;
//import Server.Interfaces.Service;
import models.*;

import java.util.List;

public class ProductsAndServiceService /*implements Service<ProductsAndService>*/ {
    private ProductsAndServiceDAO productsAndServiceDAO = new ProductsAndServiceDAO();

    public ProductsAndServiceService() {
    }

    public ProductsAndService findEntity(int id) {
        ProductsAndService entity = (ProductsAndService)this.productsAndServiceDAO.findById(id);
        return entity;
    }

//    public ProductsAndService findEntity(String id) {
//        ProductsAndService entity = (ProductsAndService)this.productsAndServiceDAO.findById(id);
//        return entity;
//    }
    public void saveEntity(ProductsAndService entity) {
        this.productsAndServiceDAO.save(entity);
    }

    public void deleteEntity(ProductsAndService entity) {
        this.productsAndServiceDAO.delete(entity);
    }

    public void updateEntity(ProductsAndService entity) {
        this.productsAndServiceDAO.update(entity);
    }

    public List<ProductsAndService> findAllEntities() {
        return this.productsAndServiceDAO.findAll();
    }
    public ProductsAndService findAllProductsAndServicesofCompany(int idcompany){return this.findAllProductsAndServicesofCompany(idcompany);}
}