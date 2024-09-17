package com.example.Project3_v1.service;

import com.example.Project3_v1.dto.product.ProductCreateRequest;
import com.example.Project3_v1.dto.product.ProductUpdateRequest;
import com.example.Project3_v1.entity.Product;
import com.example.Project3_v1.entity.Store;
import com.example.Project3_v1.entity.User;
import com.example.Project3_v1.repository.ProductRepository;
import com.example.Project3_v1.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;


    //Create
    public Product createProduct (Integer storeId, ProductCreateRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setImage(productRequest.getImage());
        newProduct.setDescription(productRequest.getDescription());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setStock(productRequest.getStock());

        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) return null;
        Store store = optionalStore.get();

        newProduct.setStore(store);
        return productRepository.save(newProduct);
    }

    //Read all
    public List<Product> getProducts() { return productRepository.findAll(); }

    //Read ones
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"))
                ;}

    //Update
    public Product updateProduct(Integer storeId, Integer productId, ProductUpdateRequest productUpdateRequest) {

        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) return null;
        Store store = optionalStore.get();

        if (!store.getProducts().contains(productRepository.findById(productId).orElse(null))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have to right to update this product");
        }

        Product product = getProductById(productId);
        product.setName(productUpdateRequest.getName());
        product.setImage(productUpdateRequest.getImage());
        product.setDescription(productUpdateRequest.getDescription());
        product.setPrice(productUpdateRequest.getPrice());
        product.setStock(productUpdateRequest.getStock());
        return productRepository.save(product);
    }

    //Delete
    public void deleteProduct (Integer storeId, Integer productId) {

        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) return;
        Store store = optionalStore.get();

        if (!store.getProducts().contains(productRepository.findById(productId).orElse(null))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Store does not have this product");
        }
        productRepository.deleteById(productId);
    }

    //Search by product name
    public List<Product> searchProducts (String keyword) {
        // Create an empty product list to return
        List<Product> returnProductList = new ArrayList<Product>();

        // Find all products that have name containing a string of keyword
        List<Product> findProductList = productRepository.findProductsByName(keyword);

        if (findProductList.isEmpty())
        {
            // If there are no products, then return empty list
            return returnProductList;
        }
        else
        {
            // If there are products, then add to return list
            return findProductList;
        }
    }

    //Search by price range
    public List<Product> searchProductsByPriceRange (Integer minPrice, Integer maxPrice) {
        List<Product> returnProductList = new ArrayList<Product>();
        List<Product> findProductList = productRepository.findProductsByPriceRange(minPrice, maxPrice);

        if (findProductList.isEmpty()) {
            return returnProductList;
        } else {
            return findProductList;
        }
    }
}
