package com.tu.controller.shop;


import com.tu.model.Order;
import com.tu.repository.CategoryRepository;
import com.tu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopPageController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/shopPage")
    public String shopPage(Model model, Pageable pageable){
        model.addAttribute("order",new Order());
        model.addAttribute("list",productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findByDeletedIsFalse(pageable));
        return "shop/shop-page";
    }
}
