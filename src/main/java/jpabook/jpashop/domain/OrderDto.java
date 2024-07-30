package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class OrderDto {

    private long id;
    private long memberId;
    private long itemId;
    private int count;

    //담아올 데이터 넣기
    private List<OrderDto> orderDtoList;

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", itemId=" + itemId +
                ", count=" + count +
                ", orderDtoList=" + orderDtoList +
                '}';
    }

}
