package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.purchase.PurchaseItemRequest;
import com.example.Project3_v1.entity.*;
import com.example.Project3_v1.repository.PurchaseBillRepository;
import com.example.Project3_v1.repository.PurchaseItemRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseBillRepository purchaseBillRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;


    //add items

    public PurchaseBill createCart (Integer userId) {
            User buyer  = userService.getUserById(userId);
            PurchaseBill purchaseBill = new PurchaseBill();
            purchaseBill.setBuyer(buyer);
            purchaseBill.setPurchaseStatus("NO PURCHASE");
            List<PurchaseItem> newEmptyList = new ArrayList<PurchaseItem>();
            purchaseBill.setPurchaseItems(newEmptyList);
            purchaseBill.setTotalAmount(0);
        return purchaseBillRepository.save(purchaseBill);
    }

        //ReadAllById
    public PurchaseBill getCart (Integer userId) {
        User buyer  = userService.getUserById(userId);
        List<PurchaseBill> purchaseBills = buyer.getPurchaseBills();
        for(PurchaseBill purchaseBill : purchaseBills) {
            if(purchaseBill.getPurchaseStatus().equals("NO PURCHASE")) {
                return purchaseBill;
            }
        }
        return null;
    }

    public PurchaseBill addToCart (
        Integer userId,
        PurchaseItemRequest purchaseRequest) {

        // Lay cart cua user hien tai tu userId
        PurchaseBill currentCart = getCart(userId);
        if(currentCart == null) {
            // Tao cart moi neu chua co
            currentCart = createCart(userId);
        }

        // Lay productId va so luong productQuantity can mua tu purchaseRequest
        Integer productId = purchaseRequest.getProductId();
        Integer productQuantity = purchaseRequest.getQuantity();

        // Tim product co id = productId
        Product product = productService.getProductById(productId);

        // Tao va luu purchaseItem bao gom (product,quantity,currentCart)
        PurchaseItem newPurchaseItem = new PurchaseItem();
        newPurchaseItem.setProduct(product);
        newPurchaseItem.setQuantity(productQuantity);
        newPurchaseItem.setPurchaseBill(currentCart);

        purchaseItemRepository.save(newPurchaseItem);

        System.out.println("newPurchaseItem: "
                + newPurchaseItem.getId() + " | "
                + newPurchaseItem.getPurchaseBill().getId() + " | "
                + newPurchaseItem.getProduct().getName() + " | "
                + newPurchaseItem.getQuantity());

        // Lay cart cua user sau khi them item moi va tinh tien totalAmount
        PurchaseBill currentPurchaseBill = getCart(userId);

        // Cap nhat tong tien
        List<PurchaseItem> purchaseItems = currentPurchaseBill.getPurchaseItems();
        int newTotalAmount = 0;
        for(PurchaseItem purchaseItem : purchaseItems) {
            newTotalAmount += (purchaseItem.getProduct().getPrice() * purchaseItem.getQuantity());
        }
        currentPurchaseBill.setTotalAmount(newTotalAmount);

        // Luu lai thong tin cart moi va tra ve
        return purchaseBillRepository.save(currentPurchaseBill);
    }
    public PurchaseBill requestToPayment (Integer userId){
        // Lay cart cua user hien tai tu userId
        PurchaseBill currentCart = getCart(userId);
        // Chuyen trang thai cart sang request to payment
        currentCart.setPurchaseStatus("REQUEST TO PAYMENT");
        // Luu lai thong tin cart moi va tra ve
        return purchaseBillRepository.save(currentCart);
    }

    //Transfer the amount required for the purchase
    public PurchaseBill transfer (Integer billId, Integer amount) {
        PurchaseBill purchaseBill = purchaseBillRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        // Kiểm tra nếu hóa đơn đã thanh toán
        if ("REQUEST TO PAYMENT".equals(purchaseBill.getPurchaseStatus())) {
            throw new RuntimeException("There is no bill waiting for payment");
        }



        // Cập nhật trạng thái và ngày thanh toán
//        purchaseBill.setStatus("PAID");
//        purchaseBill.setPaymentDate(LocalDateTime.now());

        // Lưu hóa đơn
        return purchaseBillRepository.save(purchaseBill);
    }
    }

}
