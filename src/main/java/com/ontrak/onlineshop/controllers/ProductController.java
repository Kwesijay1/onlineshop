package com.ontrak.onlineshop.controllers;

import com.ontrak.onlineshop.models.ProductDto;
import com.ontrak.onlineshop.models.Products;
import com.ontrak.onlineshop.services.ProductRepository;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Products> products = repo.findAll(Sort.by(Sort.Direction.DESC,"Id" ));
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/createProduct";
    }

    @PostMapping("/create")
    public String createProduct (
       @Valid @ModelAttribute ProductDto productDto,
               BindingResult result){

        if(productDto.getImageFile() .isEmpty()){
            result.addError(new FieldError("productDto", "imageFile", "Image file is required"));
        }

        if (result.hasErrors()) {
            return "products/createProduct";
        }

            // save image file
            MultipartFile image = productDto.getImageFile();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

            try {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            Products products = new Products();
            products.setName(productDto.getName());
            products.setDescription(productDto.getBrand());
            products.setCategory(productDto.getCategory());
            products.setPrice(productDto.getPrice());
            products.setDescription(productDto.getDescription());
            products.setCreatedAt(createdAt);
            products.setImageFileName(storageFileName);

            repo.save(products);

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int Id){

        try{
        Products products = repo.findById(Id).get();
        model.addAttribute("products", products);

            ProductDto productDto = new ProductDto();
            productDto.setName(products.getName());
            productDto.setDescription(products.getBrand());
            productDto.setCategory(products.getCategory());
            productDto.setPrice(products.getPrice());
            productDto.setDescription(products.getDescription());

            model.addAttribute("productDto", productDto);
        }

        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/Products";
        }
        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int Id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ){
        try {
            Products products = repo.findById(Id).get();
            model.addAttribute("products", products);

            if (result.hasErrors()) {
                return "products/EditProduct";
            }

            if (productDto.getImageFile().isEmpty()) {
                // delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + products.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

// save new image file
                MultipartFile image = productDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                products.setImageFileName(storageFileName);

            }

            products.setName(productDto.getName());
            products.setDescription(productDto.getBrand());
            products.setCategory(productDto.getCategory());
            products.setPrice(productDto.getPrice());
            products.setDescription(productDto.getDescription());

            repo.save(products);
        }
        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/products/";
    }

    @GetMapping("/delete")
    public String deleteProduct(
            Model model,
            @RequestParam int Id){

        try{
            Products products = repo.findById(Id).get();

            //delete product image
            Path imagePath = Paths.get("/public/images" + products.getImageFileName());
            try{
                Files.delete(imagePath);
            }
            catch (Exception e){
                System.out.println("Exception: " + e.getMessage());
            }

            //delete the product
            repo.delete(products);
        }
        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/products/";
    }
}
