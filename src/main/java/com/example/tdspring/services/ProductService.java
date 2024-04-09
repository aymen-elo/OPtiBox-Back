package com.example.tdspring.services;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.Product;
import com.example.tdspring.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product updateProduct(Product product) throws DBException, NotFoundException {
        Product existing;
        log.info("Service : Updating product ...");

        if (product.getId() != null) {
            existing = this.productRepository.findById(product.getId()).orElse(null);
            if (existing == null) throw new NotFoundException("Could not find product with id : " + product.getId());
        } else {
            existing = new Product();
        }

        existing.setTitle(product.getTitle());
        existing.setType(product.getType());
        existing.setSize(product.getSize());
        existing.setCmu(product.getCmu());
        existing.setLocation(product.getLocation());
        existing.setPicture(product.getPicture());
        // if picture == "null" or null, set picture to null
        if (existing.getPicture() == null || existing.getPicture().equals("null")) {
            existing.setPicture(null);
        }

        try {
            Product productCreated = this.productRepository.save(existing);
            return productCreated;
        } catch (Exception e) {
            throw new DBException("Could not create product");
        }
    }

    public Product deleteProduct(Long id) throws NotFoundException, DBException {
        Product existing = this.productRepository.findById(id).orElse(null);
        if (existing == null) throw new NotFoundException("Could not find product with id : " + id);

        try {
            this.productRepository.delete(existing);
            return existing;
        } catch (Exception e) {
            throw new DBException("Could not delete product");
        }
    }
}
