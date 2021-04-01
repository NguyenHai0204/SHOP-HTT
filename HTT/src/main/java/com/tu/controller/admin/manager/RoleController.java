package com.tu.controller.admin.manager;


import com.tu.model.Role;
import com.tu.repository.RoleRepository;
import com.tu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String showlist(Model model, Pageable pageable) {
        model.addAttribute("list", roleService.showAll(pageable));
        return "admin/manager/role/list-role";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin/manager/role/add-role";
    }

    @PostMapping("/add")
    public String doAdd(@Valid @ModelAttribute("role") Role role, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/manager/role/add-role";
        }
        roleService.save(role);
        attributes.addFlashAttribute("mess", "Thêm mới thành công...!!!");

        return "redirect:/role";
    }



}