package com.luxoft.osh.onlineshop.web;

import com.luxoft.osh.onlineshop.entity.Product;
import com.luxoft.osh.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductsController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductService productService;

    @GetMapping()
    public List<Product> showAllProducts() {
        List<Product> products = productService.findAll();
        logger.info("products {}", products);
        return products;
    }



//    @GetMapping()
//    protected String showAllProducts(HttpServletRequest req, Model model) {
//        dataForProductsList(req, model);
//        return "products_list";
//    }

//    private void dataForProductsList(HttpServletRequest req, Model model) {
//        List<Product> products = productService.findAll();
//        HttpSession session = req.getSession();
//        String email = (String) session.getAttribute("usrEmail");
//        model.addAttribute("products", products);
//        model.addAttribute("usrEmail", email);
//        model.addAttribute("login", Boolean.toString(securityService.isAuth(req)));
//    }
}
