package com.example.demo.Controllers;

import com.example.demo.Entity.Product;
import com.example.demo.Repo.productRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Optional;

@EnableSwagger2
@Controller
public class MainController {

    @Autowired
    private productRepository productRepo;

    @GetMapping("/")
    public String main(Model model){
        Iterable<Product> products = productRepo.findAll();
        model.addAttribute("products",products);
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

    @GetMapping("/new-product")
    public String productAdd(Model model){
        return "product-add";
    }

    @PostMapping("/new-product")
    public String productNewAdd(@RequestParam String name, @RequestParam String price,
                                @RequestParam String info, @RequestParam String imageUrl,@RequestParam String type, Model model){
        Product product = new Product(name,price,info,imageUrl,type);
        productRepo.save(product);
        return "redirect:/";
    }

    @GetMapping("/categories")
    public String getProductByCategory(Model model) {
        Iterable<Product> Fruits = productRepo.findAllByType("Fruits");
        Iterable<Product> Vegetables = productRepo.findAllByType("Vegetables");
        Iterable<Product> Berries = productRepo.findAllByType("Berries");
        model.addAttribute("Fruits",Fruits);
        model.addAttribute("Vegetables",Vegetables);
        model.addAttribute("Berries",Berries);
        return "categories";
    }

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable(value = "id") long id, Model model){
        if(!productRepo.existsById(id)){
            return "redirect:/";
        }
        Optional<Product> product = productRepo.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product",res);
        return "product-detail";
    }
    @PostMapping("/product/{id}/delete")
    public String productDelete(@PathVariable(value = "id") long id, Model model){
        Product product = productRepo.findById(id).orElseThrow();
        productRepo.delete(product);
        return "redirect:/";
    }
    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        if(!productRepo.existsById(id)) {
            return "redirect:/";
        }

        Optional<Product> product = productRepo.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "product-edit";
    }

    @PostMapping("/product/{id}/edit")
    public  String productPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String price, @RequestParam String url, Model model){
        Product product = productRepo.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);
        product.setImageUrl(url);
        productRepo.save(product);
        return  "redirect:/";

    }

}
