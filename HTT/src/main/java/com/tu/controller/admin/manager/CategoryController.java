package com.tu.controller.admin.manager;


import com.tu.model.Category;
import com.tu.model.Product;
import com.tu.repository.CategoryRepository;
import com.tu.repository.ProductRepository;
import com.tu.service.CategoryService;
import com.tu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepository productRepository;

    @InitBinder

    public void initBinder(WebDataBinder dataBinder) {

        Object target = dataBinder.getTarget();

        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == Category.class) {
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("")
    public String showList(Model model,  @PageableDefault(size = 7,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("list", categoryService.showAll(pageable));
        return "admin/manager/category/list-category";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/manager/category/add-category";
    }

    @PostMapping("/add")
    public String doAdd(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return "admin/manager/category/add-category";
            }
            String name = category.getName();
            List<Category> categoryTemps = categoryRepository.findByName(name);
            if (categoryTemps == null || categoryTemps.isEmpty()) {
                categoryService.save(category);
                attributes.addFlashAttribute("mess", "Thêm mới thành công...!!!");
            } else {
                attributes.addFlashAttribute("mess", "Tên Category đã tồn tại");
            }
            return "redirect:/category";
        } catch (Exception e) {
            attributes.addFlashAttribute("mess", "Rrororo");
            return "redirect:/category";
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/manager/category/edit-category";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit")
    public String doEdit(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return "admin/manager/category/edit-category";
            }

            List<Category> categories = categoryRepository.findAll();
            for (Category category11 : categories) {
                if ((category.getName()).equals(category11.getName()) && (category.getId()) != category11.getId()) {
                    attributes.addFlashAttribute("mess", "Tên đã tồn tại...!!!");
                    return "redirect:/category";
                }
            }
            categoryService.save(category);
            attributes.addFlashAttribute("mess", "Thay đổi thành công...!!!");
        } catch (Exception e) {
            e.getMessage();
            attributes.addFlashAttribute("mess", "Error");
        }
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "xóa thành công");
        return "redirect:/category";
    }


    @GetMapping("/showDeleteCategory")
    public String showDelete(Model model,   @PageableDefault(size = 7,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("list", categoryRepository.findAllByDeletedIsTrue(pageable));
        return "admin/manager/category/list-delete-category";
    }

    @GetMapping("/reset/{id}")
    public String reset(@PathVariable long id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id).orElseThrow();
        category.setDeleted(false);
        categoryService.save(category);
//        // loi ve list product co cung cat id
//        List<Product> product = productRepository.findAllByCategoryId(id);
//        // chay vong lap qua list
//        for (Product product1 : product) {
//                product1.setDeleted(false);
//                productRepository.save(product1);
//        }
//        // set isDelete cua moi cai thanh false
//
//        // save all
        redirectAttributes.addFlashAttribute("mess", "khôi phục thành công");
        return "redirect:/category/showDeleteCategory";
    }
}
