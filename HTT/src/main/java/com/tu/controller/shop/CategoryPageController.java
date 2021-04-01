package com.tu.controller.shop;


import com.tu.model.Order;
import com.tu.repository.CategoryRepository;
import com.tu.repository.ProductRepository;
import com.tu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryPageController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/categoryPage/{id}")
    public String categoryPage(@PathVariable Long id, Model model, Pageable pageable){
        model.addAttribute("order",new Order());
        model.addAttribute("list",productRepository.findAllByCategoryId(id,pageable));
        model.addAttribute("categories", categoryRepository.findByDeletedIsFalse(pageable));
        return "shop/category-page";
    }
}