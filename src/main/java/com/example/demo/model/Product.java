package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Length(min = 3, max = 30, message = "min 3 max 30 chars")
    private String name;
    @Length(max = 250)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private int quantity;
    @Length(max = 1000)
    private String imageUrl;

    public Product(Long id, String name, String description, Double price, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ItemCart> itemCarts;

    public Product() {

    }


    @Transient
    public int getTotalUnitsInOrder() {
        int sum = 0;
        List<ItemCart> itemCarts = getItemCarts();
        if (itemCarts == null) {
            return 0;
        } else
            for (ItemCart op : itemCarts) {
                sum += op.getQuantity();
            }
        return sum;
    }

    @Transient
    public int getCurrentTotalUnitsInStock() {
        return getQuantity() - getTotalUnitsInOrder();
    }
}


