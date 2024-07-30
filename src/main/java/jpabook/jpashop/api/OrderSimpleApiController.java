package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.SimpleOrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*

order에서   order->member
           order->delivery

**/


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
     public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for(Order order :all){
            order.getMember().getName(); //lazy 강제초기화
            order.getDelivery().getAddress(); //lazy 강제초기화
            //지연로딩해놔서 postman에서 null값뜨는 맴버 딜리버리 보이게하기
        }
        return all;
    }
@GetMapping("/api/v2/simple-orders")
public List<SimpleOrderDto> orverV2(){
    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
    return result;
}


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orverV3(){
        List<Order> orders = orderRepository.findAllMemberDelivery();
        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;


    }
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> orverV4() {
      return  orderRepository.findOrderDtos();

}


@Data
static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

    public SimpleOrderDto(Order order) {
        orderId=order.getId();
        name=order.getMember().getName();
        orderDate=order.getOrderDate();
        orderStatus=order.getStatus();
        address=order.getDelivery().getAddress();
    }
}
}
