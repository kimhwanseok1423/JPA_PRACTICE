package jpabook.jpashop.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.item.Item;
import javax.persistence.*;
@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문
    // 주문 가격
    private int orderPrice;
    private int count; //주문 수량

    protected  OrderItem(){

    }
    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice,int count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
//비즈니스 로직
    public void cancle() {
        getItem().addStock(count);
    }
//총가격
    public int getTotalPrice() {
return getOrderPrice()*getCount();
    }
}