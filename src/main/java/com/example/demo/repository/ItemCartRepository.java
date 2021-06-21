package com.example.demo.repository;

import com.example.demo.model.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    List<ItemCart> findByProduct(Long id);

}
