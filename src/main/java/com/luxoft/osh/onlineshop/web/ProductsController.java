package com.luxoft.osh.onlineshop.web;

import com.luxoft.osh.onlineshop.entity.Product;
import com.luxoft.osh.onlineshop.service.ProductService;
import com.luxoft.osh.onlineshop.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductService productService;
    private final SecurityService securityService;

//    @GetMapping()
//    @ResponseBody
//    public List<Product> showAllProducts() {
//        List<Product> products = productService.findAll();
//        logger.info("products {}", products);
//        return products;
//    }

    @GetMapping()
    protected String showAllProducts(HttpServletRequest req, Model model) {
        dataForProductsList(req, model);
        return "products_list";
    }

    @GetMapping("/add")
    protected String getAddProductPage() {
        return "add_product";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(@RequestBody Product product, Model model) {
        String name = product.getName();
        double price = product.getPrice();
        try {
            if (name != null && name.length() > 0 && price > 0) {
                logger.info("add product {}", product);
                productService.add(product);
                String msgSuccess = "Product <i>" + name + "</i> was successfully added!";
                model.addAttribute("msgSuccess", msgSuccess);
                return "add_product";

            } else {
                String errorMsg = "Please fill up all fields!";
                model.addAttribute("errorMsg", errorMsg);
                return "add_product";
            }
        } catch (Exception e) {
            String errorMsg = "Please fill up all fields!";
            model.addAttribute("errorMsg", errorMsg);
            return "add_product";
        }
    }

    @PostMapping("/delete")
    protected String deleteProduct(@RequestParam int id, HttpServletRequest req, Model model) {
        Product product = productService.productFindById(id);
        String msgSuccess = "Product " + product.getName() + " was successfully deleted!";
        model.addAttribute("msgSuccess", msgSuccess);
        productService.remove(id);
        dataForProductsList(req, model);
        return "products_list";
    }


    private void dataForProductsList(HttpServletRequest req, Model model) {
        List<Product> products = productService.findAll();
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("usrEmail");
        model.addAttribute("products", products);
        model.addAttribute("usrEmail", email);
        model.addAttribute("login", Boolean.toString(securityService.isAuth(req)));
    }
}
