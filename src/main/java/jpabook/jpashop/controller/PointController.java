package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import jpabook.jpashop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PointController {
    private final PaymentService pointService;
    private final MemberService memberService;
    private final OrderService orderService;
    public  @ResponseBody void chargePoint(OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);
    }

}
