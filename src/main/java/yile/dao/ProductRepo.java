package yile.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import yile.model.po.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
