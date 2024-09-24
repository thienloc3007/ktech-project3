package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.purchase.PurchaseItemRequest;
import com.example.Project3_v1.entity.*;
import com.example.Project3_v1.repository.PurchaseBillRepository;
import com.example.Project3_v1.repository.PurchaseItemRepository;
import com.example.Project3_v1.repository.StoreRepository;
import com.example.Project3_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;


    //add items

    public PurchaseBill createCart(Integer userId) {
        User buyer = userService.getUserById(userId);
        PurchaseBill purchaseBill = new PurchaseBill();
        purchaseBill.setBuyer(buyer);
        purchaseBill.setPurchaseStatus("NO PURCHASE");
        List<PurchaseItem> newEmptyList = new ArrayList<PurchaseItem>();
        purchaseBill.setPurchaseItems(newEmptyList);
        purchaseBill.setTotalAmount(0);
        return purchaseBillRepository.save(purchaseBill);
    }

    //ReadAllById
    public PurchaseBill getCart(Integer userId) {
        User buyer = userService.getUserById(userId);
        List<PurchaseBill> purchaseBills = buyer.getPurchaseBills();
        for (PurchaseBill purchaseBill : purchaseBills) {
            if (purchaseBill.getPurchaseStatus().equals("NO PURCHASE")) {
                return purchaseBill;
            }
        }
        return null;
    }

    public PurchaseBill addToCart(
            Integer userId,
            PurchaseItemRequest purchaseRequest) {

        // Lay cart cua user hien tai tu userId
        PurchaseBill currentCart = getCart(userId);
        if (currentCart == null) {
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
        for (PurchaseItem purchaseItem : purchaseItems) {
            newTotalAmount += (purchaseItem.getProduct().getPrice() * purchaseItem.getQuantity());
        }
        currentPurchaseBill.setTotalAmount(newTotalAmount);

        // Luu lai thong tin cart moi va tra ve
        return purchaseBillRepository.save(currentPurchaseBill);
    }

    public PurchaseBill requestToPayment(Integer userId) {
        User buyer = userService.getUserById(userId);

        // Lay cart cua user hien tai tu userId
        PurchaseBill currentCart = getCart(userId);


        //thực hiện thanh toán
        //check tra total amount to pay
        Integer totalAmount = currentCart.getTotalAmount();
        // check balance
        Integer balance = buyer.getBalance();

        // check if total amount > balance
        // if totalAmount > balance, throw exception
        if (totalAmount > balance) {
            throw new RuntimeException("Insufficient balance");
        } else {
            // trừ tiền và cập nhật balance
            buyer.setBalance(balance - totalAmount);
            userRepository.save(buyer);

            // Cập nhật trạng thái và ngày thanh toán
            currentCart.setPurchaseStatus("REQUEST TO PAYMENT");
        }

        List<PurchaseItem> purchaseItems = currentCart.getPurchaseItems();
        for (PurchaseItem purchaseItem : purchaseItems) {
            Product product = purchaseItem.getProduct();
            Store store = product.getStore();
            User owner = store.getOwner();
            Integer ownerId = owner.getId();

            //call for the seller of current cart
            List<User> sellers = userService.getSellers();
            for (User seller : sellers) {
                Integer sellerBalance = seller.getBalance();
                if (Objects.equals(seller.getId(), ownerId)) {
                    seller.setBalance(sellerBalance + currentCart.getTotalAmount());
                    userRepository.save(seller);
                }
            }
        }

        // Lưu hóa đơn
        return purchaseBillRepository.save(currentCart);
    }

    public PurchaseBill cancelPayment(Integer userId, Integer billId) {
        User buyer = userService.getUserById(userId);

        // Lay cart đã thanh toán cua user hien tai tu userId

        Optional<PurchaseBill> targetBill = purchaseBillRepository.findById(billId);
        String compareStatus = targetBill.get().getPurchaseStatus();
        String targetStatus = "REQUEST TO PAYMENT";
        if (!Objects.equals(compareStatus, targetStatus)) {
            throw new RuntimeException("You cannot cancel a purchase");
        }
        // Cập nhật trạng thái hóa đơn
        targetBill.get().setPurchaseStatus("CANCELLED");
        // Hoàn tiền
        buyer.setBalance(buyer.getBalance() + targetBill.get().getTotalAmount());
        userRepository.save(buyer);
        // Lưu lại thông tin hóa đơn
        return purchaseBillRepository.save(targetBill.get());
    }

    public PurchaseBill acceptPurchaseBill(Integer userId, Integer billId, Integer itemId) {
        //search những purchase bill của store có trạng thái "REQUEST TO PAYMENT"
        List<PurchaseBill> targetBills = purchaseBillRepository.findBillRequestToPayment();
        if (targetBills.isEmpty()) {
            throw new IllegalStateException("No purchase bills found with 'REQUEST TO PAYMENT' status.");
        }

            PurchaseBill targetBill = targetBills.stream()
                    .filter(bill -> Objects.equals(bill.getId(), billId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Purchase bill not found for the given billId: " + billId));

            targetBill.setPurchaseStatus("PAID");

            List<PurchaseItem> purchaseItems = targetBill.getPurchaseItems();
            for (PurchaseItem purchaseItem : purchaseItems) {
                Product product = purchaseItem.getProduct();
                Integer stockBefore = product.getStock(); //value of stock in system before purchase
                product.setStock(stockBefore - purchaseItem.getQuantity()); //value of stock in system after purchase
                purchaseItemRepository.save(purchaseItem);
            }

        return purchaseBillRepository.save(targetBill);
    }
}



