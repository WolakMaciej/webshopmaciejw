package com.example.demo.controller;

import com.example.webshopback.model.ItemCart;
import com.example.webshopback.service.ItemCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/")
public class ItemCartController {
    @Autowired
    ItemCartService itemCartService;

   @GetMapping("/itemCarts")
    public ResponseEntity<List<ItemCart>> getItemCarts() {
        List<ItemCart> itemCarts = itemCartService.getAll();
        if (CollectionUtils.isEmpty(itemCarts)) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(itemCarts, HttpStatus.OK);
    }

    @PostMapping("/itemCarts")
    public ResponseEntity<ItemCart> createNewItemCart(@Valid @RequestBody ItemCart itemCart) {
        ItemCart newItemCart = itemCartService.createNew(itemCart);
        return new ResponseEntity<>(newItemCart, HttpStatus.CREATED);
    }

    @GetMapping("/itemCarts/{id}")
    public ResponseEntity<ItemCart> getItemCart(@PathVariable long id) {
        ItemCart itemCart = itemCartService.getOne(id);
        return new ResponseEntity<>(itemCart, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/api/itemCarts/{id}")
    public ResponseEntity<ItemCart> deleteItemCart(@PathVariable long id) {
        itemCartService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/itemCarts/{id}")
    public ResponseEntity<ItemCart> updateItemCart(@PathVariable long id, @RequestBody ItemCart itemCart) {
        ItemCart newItemCart = itemCartService.getOne(id);
        newItemCart.setQuantity(itemCart.getQuantity());
        itemCartService.update(newItemCart);
        return new ResponseEntity<>(itemCart, HttpStatus.OK);
    }
}
