package com.example.Project3_v1.controller;

import com.example.Project3_v1.dto.purchase.PurchaseItemRequest;
import com.example.Project3_v1.entity.CustomUserDetails;
import com.example.Project3_v1.entity.PurchaseBill;
import com.example.Project3_v1.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired private PurchaseService purchaseService;


    @GetMapping("/viewCart")
    public PurchaseBill viewCart() {
        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        PurchaseBill currentCart = purchaseService.getCart(userDetails.getId());
        if (currentCart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "You don't have any item in current shopping cart");
        return currentCart;
    }

    @PostMapping("/add-product-to-cart")
    public PurchaseBill addToCart(
            @RequestBody PurchaseItemRequest purchaseRequest) {
        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return purchaseService.addToCart(userDetails.getId(),purchaseRequest);
    }

    @PostMapping("/request-to-payment")
    public PurchaseBill requestToPayment() {
        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return purchaseService.requestToPayment(userDetails.getId());
    }

    @DeleteMapping("/cancel-payment")
    public void cancelPayment(
            @PathVariable Integer billId
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        purchaseService.cancelPayment(userDetails.getId(), billId);
    }


}
