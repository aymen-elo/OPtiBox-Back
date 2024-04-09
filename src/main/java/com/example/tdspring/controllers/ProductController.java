package com.example.tdspring.controllers;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.Product;
import com.example.tdspring.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> postProduct(
            @RequestParam("title") String title,
            @RequestParam("type") String type,
            @RequestParam("size") String size,
            @RequestParam("cmu") String cmu,
            @RequestParam("location") String location,
            @RequestParam("picture") String pictureFile
    ) {
        try {
            log.info("Creating product ...");
            // Créer un objet Product avec les données du formulaire et l'image en base64
            Product product = new Product();
            product.setTitle(title);
            product.setType(type);
            product.setSize(size);
            product.setCmu(cmu);
            product.setLocation(location);
            product.setPicture(pictureFile);

            // Enregistrer le produit dans la base de données
            Product savedProduct = this.productService.updateProduct(product);

            // Retourner la réponse avec le produit créé ou mis à jour
            HttpStatus status = (product.getId() == null) ? HttpStatus.CREATED : HttpStatus.ACCEPTED;
            return new ResponseEntity<>(savedProduct, status);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> putProduct(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("type") String type,
            @RequestParam("size") String size,
            @RequestParam("cmu") String cmu,
            @RequestParam("location") String location,
            @RequestParam("picture") String pictureFile
    ) {
        try {
            log.info("Updating product ...");
            // Créer un objet Product avec les données du formulaire et l'image en base64
            Product product = new Product();
            product.setId(id);
            product.setTitle(title);
            product.setType(type);
            product.setSize(size);
            product.setCmu(cmu);
            product.setLocation(location);
            product.setPicture(pictureFile);

            // Enregistrer le produit dans la base de données
            Product savedProduct = this.productService.updateProduct(product);

            // Retourner la réponse avec le produit créé ou mis à jour
            HttpStatus status = (product.getId() == null) ? HttpStatus.CREATED : HttpStatus.ACCEPTED;
            return new ResponseEntity<>(savedProduct, status);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            log.info("Deleting product ...");
            return new ResponseEntity<>(this.productService.deleteProduct(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
