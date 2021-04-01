package com.tu.controller.admin.manager;


import com.tu.dto.LoginUser;
import com.tu.model.Customer;
import com.tu.model.Order;
import com.tu.repository.CategoryRepository;
import com.tu.repository.CustomerRepository;
import com.tu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import java.security.Principal;

@Controller
@RequestMapping(value = "profile" )
public class SettingController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("")
    public String profile(Model model, Principal principal, Pageable pageable) {
        model.addAttribute("order", new Order());
        model.addAttribute("customer", customerRepository.findByEmail(principal.getName()));
        model.addAttribute("categories", categoryRepository.findByDeletedIsFalse(pageable));
        return "shop/profile";
    }

    @PostMapping("/edit")
    public String doEditProfile(@Valid @ModelAttribute("customer") Customer customer,BindingResult result,RedirectAttributes attributes,HttpServletRequest request) throws IOException {
        if (result.hasFieldErrors()) {
            attributes.addFlashAttribute("mess", "Lỗi...!!!");
            return "redirect:/profile/";
        }
        if ((customer.getPassword()).equals(customer.getConfigPassword())) {
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
                    return "redirect:/profile/";
                }
            }
            customerService.save(customer);
            attributes.addFlashAttribute("mess", "Thay đổi thành công...!!!");
        } else {
            attributes.addFlashAttribute("mess", "Mật khẩu không khớp...!!!");
        }
        return "redirect:/profile/";
    }

}

