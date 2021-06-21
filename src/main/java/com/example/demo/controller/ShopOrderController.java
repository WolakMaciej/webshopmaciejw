package com.example.demo.controller;

import com.example.webshopback.model.ShopOrder;
import com.example.webshopback.model.User;
import com.example.webshopback.service.ItemCartService;
import com.example.webshopback.service.ShopOrderService;
import com.example.webshopback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/")
public class ShopOrderController {
    @Autowired
    ShopOrderService shopOrderService;
    @Autowired
    UserService userService;
    @Autowired
    ItemCartService itemCartService;


   @GetMapping("/shopOrders")
    public ResponseEntity<List<ShopOrder>> getShopOrder() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        String autho = userDetails.getAuthorities().iterator().next().toString();
        List<ShopOrder> shopOrders;
        if (autho.equals("ADMIN")) {
            shopOrders = shopOrderService.getAll();
        }else {
            shopOrders = shopOrderService.findShopOrdersByUserUsername(username);
        }
        if (CollectionUtils.isEmpty(shopOrders)) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(shopOrders, HttpStatus.OK);
    }

    @PostMapping("/shopOrders")
    public ResponseEntity<ShopOrder> createNewShopOrder(@Valid @RequestBody ShopOrder shopOrder) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        shopOrder.setUser(user);
        shopOrderService.createNew(shopOrder);
        return new ResponseEntity<>(shopOrder, HttpStatus.CREATED);
    }

    @GetMapping("/shopOrders/{id}")
    public ResponseEntity<ShopOrder> getShopOrder(@PathVariable long id) {
        ShopOrder shopOrder = shopOrderService.getOne(id);
        return new ResponseEntity<>(shopOrder, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/api/shopOrders/{id}")
    public ResponseEntity<ShopOrder> deleteShopOrder(@PathVariable long id) {
        shopOrderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   @PutMapping("/shopOrders/{id}")
    public ResponseEntity<ShopOrder> updateShopOrder(@PathVariable long id, @RequestBody ShopOrder shopOrder) {
        ShopOrder newShopOrder = shopOrderService.getOne(id);
        newShopOrder.setUser(newShopOrder.getUser());
        shopOrderService.update(newShopOrder);
        return new ResponseEntity<>(shopOrder, HttpStatus.OK);
    }

}
