package com.tu.controller.admin.manager;

import com.tu.model.Category;
import com.tu.repository.CategoryRepository;
import com.tu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class SearchCategory {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/searchCategory")
    public String search(@RequestParam Optional<String> search , Model model, @PageableDefault(size = 5) Pageable pageable, RedirectAttributes attributes){
        Page<Category> categories = categoryService.findAllByNameContaining(search.get(),pageable);
        if (!categories.isEmpty()){
            model.addAttribute("list", categories);
            return "admin/manager/category/list-category";
        }else {
            attributes.addFlashAttribute("mess","Không tìm thấy dữ liệu nào...!!!");
            return "redirect:/category";
        }
    }
}
