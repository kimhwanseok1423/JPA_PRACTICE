package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.BuyerRepository;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final BuyerRepository buyerRepository;

    //주문   회원id  상품id 수량만 넘어옴
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();

    }

    @Transactional
    public Long orderByList(List<OrderDto> orderDtoList) {
        Member member = null;
        Item item = null;
        Delivery delivery = new Delivery();
        OrderItem[] orderItems = new OrderItem[orderDtoList.size()];
        int idx = 0;

        for (OrderDto orderDto : orderDtoList) {
            member = memberRepository.findOne(orderDto.getMemberId());
            item = itemRepository.findOne(orderDto.getItemId());

            if (idx == 0) {
                delivery.setAddress(member.getAddress());
                delivery.setStatus(DeliveryStatus.READY);
                //주문이 처리되고 배송은 안한상태
            }

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderDto.getCount());
            orderItems[idx] = orderItem;
            idx++;
        }

        Order order = Order.createOrder(member, delivery, orderItems);
        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }


    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

    @Transactional
    public void save_buyerInfo(BuyerEntity request) {
        buyerRepository.save(request);

    }
}