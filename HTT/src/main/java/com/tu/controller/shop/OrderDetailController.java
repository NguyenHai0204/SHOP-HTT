package com.tu.controller.shop;

import com.tu.model.Category;
import com.tu.model.OrderDetail;
import com.tu.repository.OrderDetailRepository;
import com.tu.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "orderDetail")
@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("")
    public String showOderDetail(Model model, Pageable pageable) {
        model.addAttribute("listOrderDetail", orderDetailService.showAll(pageable));
        return "shop/layout";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("oderDetail") OrderDetail orderDetail, BindingResult result, RedirectAttributes redirectAttributes) {
        orderDetailService.save(orderDetail);
        redirectAttributes.addFlashAttribute("mess", "đã thêm vào giỏ hàng");
        return "";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable long id, Model model){
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if(orderDetail != null){
            model.addAttribute("oderDetail",orderDetail);
            return "shop/orderDetail/edit-orderDetail";
        }else {
            return "erorr";
        }
    }
    @PostMapping("/edit")
    public String doEdit(@Valid @ModelAttribute("oderDetail") OrderDetail orderDetail,BindingResult result, RedirectAttributes attributes){
            if (result.hasErrors()){
                return "shop/orderDetail/edit-orderDetail";
            }
             orderDetailService.save(orderDetail);
            attributes.addFlashAttribute("mess","thay đổi thành công");
            return "redirect:/orderDetail";
    }
}
