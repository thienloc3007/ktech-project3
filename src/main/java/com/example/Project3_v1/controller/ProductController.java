package com.example.Project3_v1.controller;

import com.example.Project3_v1.dto.product.ProductCreateRequest;
import com.example.Project3_v1.dto.product.ProductUpdateRequest;
import com.example.Project3_v1.dto.user.UserCreationRequest;
import com.example.Project3_v1.entity.CustomUserDetails;
import com.example.Project3_v1.entity.Product;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //Create
    @PostMapping("/create")
    public Product createProduct(
            @RequestBody ProductCreateRequest productCreateRequest) {

        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        // Lấy thông tin store từ userDetails
        Store store = userDetails.getStore();

        return productService.createProduct(store.getId(), productCreateRequest);
    }

    //Read
    @GetMapping
    List<Product> getAllProducts() {
        return productService.getProducts();
    }

    //ReadOne
    @GetMapping("/{productId}")
    public Product getProductbyId(
            @PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    //Update
    @PutMapping("/{productId}/update")
    public Product updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductUpdateRequest productUpdateRequest) {

    // Lấy thông tin user từ token, truyền vào userDetails
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
            getAuthentication().getPrincipal();
    // Lấy thông tin store từ userDetails
    Store store = userDetails.getStore();

    return productService.updateProduct(store.getId(), productId, productUpdateRequest);
    }

    //Delete
    @DeleteMapping("/{productId}/delete")
    public String deleteProduct(
            @PathVariable Integer productId) {

        // Lấy thông tin user từ token, truyền vào userDetails
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        // Lấy thông tin store từ userDetails
        Store store = userDetails.getStore();
      productService.deleteProduct(store.getId(), productId);
      return "This product has been deleted";
    }

    @GetMapping("/search-by-products")
    List<Product> searchProducts(
            @RequestParam("name") String keyword) {
        return productService.searchProducts(keyword);
    }

    @GetMapping("/search-by-price-range")
    List<Product> searchProductsByPriceRange(
            @RequestParam("minPrice") Integer minPrice,
            @RequestParam("maxPrice") Integer maxPrice) {
        return productService.searchProductsByPriceRange(minPrice, maxPrice);
    }

}
