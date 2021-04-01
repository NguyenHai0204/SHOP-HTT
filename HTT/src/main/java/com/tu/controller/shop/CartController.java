package com.tu.controller.shop;

import com.tu.model.Order;
import com.tu.model.OrderDetail;
import com.tu.model.Product;
import com.tu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "cart/")
public class CartController {

    @Autowired
    private ProductService productService;

    @GetMapping("add/{id}")
    public String doAdd(@PathVariable("id") long id, HttpSession session, HttpServletRequest request, Model model) {
        double total = 0;
        Product product = productService.findById(id).orElseThrow();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(1);
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            order = new Order();
            order.setOrderDetails(new ArrayList<>());
            order.getOrderDetails().add(orderDetail);
            session.setAttribute("order", order);
            total = orderDetail.getProduct().getPrice() * orderDetail.getQuantity();
            session.setAttribute("total", total);
            return "redirect:" + request.getHeader("referer");
        }
        boolean hasOne = false;
        for (OrderDetail orderDetail1 : order.getOrderDetails()) {
            if (orderDetail1.getProduct().getId() == id) {
                orderDetail1.setQuantity(orderDetail1.getQuantity() + 1);
                hasOne = true;
            }
        }
        if (!hasOne) {
            order.getOrderDetails().add(orderDetail);
        }
        for (OrderDetail orderDetail1 : order.getOrderDetails()) {
            total += orderDetail1.getProduct().getPrice() * orderDetail1.getQuantity();
        }
        model.addAttribute("order",new Order());
        session.setAttribute("size", order.getOrderDetails().size());
        session.setAttribute("order", order);
        session.setAttribute("total", total);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("delete/{id}")
    public String doDelete(@PathVariable("id") long id,HttpSession session, HttpServletRequest request,Model model){
        Order order = (Order) session.getAttribute("order");
        OrderDetail detail = null;
        for(OrderDetail orderDetail :order.getOrderDetails()){
            if(orderDetail.getProduct().getId() == id ){
                detail= orderDetail;
                break;
            }
        }
        double total = 0;
        order.getOrderDetails().remove(detail);
        for (OrderDetail orderDetail1 : order.getOrderDetails()) {
            total += orderDetail1.getProduct().getPrice() * orderDetail1.getQuantity();
        }
        model.addAttribute("order",new Order());
        session.setAttribute("size", order.getOrderDetails().size());
        session.setAttribute("order", order);
        session.setAttribute("total", total);
        return "redirect:" + request.getHeader("referer");
    }
}
