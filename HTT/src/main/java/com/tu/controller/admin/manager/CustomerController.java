package com.tu.controller.admin.manager;

import com.tu.model.Category;
import com.tu.model.Customer;
import com.tu.repository.CustomerRepository;
import com.tu.repository.RoleRepository;
import com.tu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Customer.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("")
    public String showList(Model model, @PageableDefault(size = 7, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("list", customerService.showAll(pageable));
        return "admin/manager/customer/list-customer";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("customer", new Customer());
        return "admin/manager/customer/add-customer";
    }


    @PostMapping("/doAdd")
    public String doAdd(@Valid @ModelAttribute("customer") Customer customer,
                        BindingResult result,
                        RedirectAttributes attributes,
                        HttpServletRequest request,
                        Pageable pageable) throws IOException {
        if (result.hasErrors()) {
            return "admin/manager/customer/add-customer";
        }

        Page<Customer> customerTemp = customerRepository.findByEmail(pageable, customer.getEmail());
        if (customerTemp.isEmpty() && (customer.getPassword()).equals(customer.getConfigPassword())) {
            String uploadRootPath = request.getServletContext().getRealPath("upload");

            File uploadRootDir = new File(uploadRootPath);

            String uploadLocalPath = "D:\\Code Gym\\SHOP-HTT\\CS-HTT\\HTT\\src\\main\\webapp\\upload";

            File uploadLocalDir = new File(uploadLocalPath);

            uploadRootDir.mkdirs();

            CommonsMultipartFile[] fileDatas = customer.getImageMulti();

            for (CommonsMultipartFile fileData : fileDatas) {
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);
                if (name != null && name.length() > 0) {
                    //ghi file vao server
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                    FileOutputStream fosServer = new FileOutputStream(serverFile);
                    BufferedOutputStream streamServer = new BufferedOutputStream(fosServer);
                    streamServer.write(fileData.getBytes());
                    streamServer.close();

                    //ghi file vao local
                    File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);
                    FileOutputStream fosLocal = new FileOutputStream(localFile);
                    BufferedOutputStream streamLocal = new BufferedOutputStream(fosLocal);
                    streamLocal.write(fileData.getBytes());
                    streamLocal.close();
                        customer.setImage(name);
                        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
                    customerService.save(customer);
                    attributes.addFlashAttribute("mess", "Thêm mới thành công...!!!");
                    return "redirect:/customer";
                }
            }
        }

        attributes.addFlashAttribute("mess", "Mật khẩu không khớp...!!!");
        return "redirect:/customer/add";
    }



    @GetMapping("/edit/{id}")
    public String doEditForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.findById(id).orElseThrow();
        if (customer != null) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("customer", customer);
            return "admin/manager/customer/edit-customer";
        } else {
            return "error";
        }
    }

    @PostMapping("/doEdit")
    public String doEdit(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, RedirectAttributes attributes) {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("mess", "Lỗi...!!!");
            return "redirect:/customer";
        }
        if ((customer.getPassword()).equals(customer.getConfigPassword())) {
            customerService.save(customer);
            attributes.addFlashAttribute("mess", "Thay đổi thành công...!!!");
        } else {
            attributes.addFlashAttribute("mess", "Mật khẩu không khớp...!!!");
        }
        return "redirect:/customer";
    }


    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);
        redirectAttributes.addFlashAttribute("mess", "xóa thành công");
        return "redirect:/customer";
    }

    @GetMapping("/showDeleteCustomer")
    public String showDelete(Model model, @PageableDefault(size = 7, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("list", customerRepository.findAllByDeletedIsTrue(pageable));
        return "admin/manager/customer/list-delete-customer";
    }

    @GetMapping("/reset/{id}")
    public String reset(@PathVariable long id, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findById(id).orElseThrow();
        customer.setDeleted(false);
        customerService.save(customer);

        redirectAttributes.addFlashAttribute("mess", "khôi phục thành công");
        return "redirect:/customer/showDeleteCustomer";
    }
}