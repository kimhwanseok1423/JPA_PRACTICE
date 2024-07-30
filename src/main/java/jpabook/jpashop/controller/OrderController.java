package jpabook.jpashop.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.ItemDto;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import jpabook.jpashop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final PaymentService paymentService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        //List<Item> items = itemService.finditems();
        List<ItemDto> items = itemService.findItemDtoList();
        System.out.println(items);
        model.addAttribute("members", members);
        model.addAttribute("items", items);


        return "order/orderForm";

    }

    @PostMapping(value = "/order")
    public String order(OrderDto orderDto, Model model) {

        ///    orderService.order(memberId, itemId, count);
        Long order = orderService.orderByList(orderDto.getOrderDtoList());


        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch
                                    orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);


        model.addAttribute("orders", orders);

        return "order/orderList";
    }
    @ResponseBody
     @PostMapping("/payment/prepare")
    public void preparePayment(@RequestBody PrePaymentEntity request)
            throws IamportResponseException, IOException {
        paymentService.postPrepare(request);
    }


    @PostMapping("/payment/validate")
    public Payment validatePayment(@RequestBody PaymentDTO request)
            throws IamportResponseException, IOException {
        return paymentService.validatePayment(request);
    }


    @PostMapping("/save_buyerInfo")
    @ResponseBody
    public void save_buyerInfo(@RequestBody BuyerEntity request) {
        orderService.save_buyerInfo(request);
    }
}
