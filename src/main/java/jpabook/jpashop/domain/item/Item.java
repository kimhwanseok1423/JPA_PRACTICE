package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.ItemForm;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//한테이블에 몰아서 넣을거라 single table 저런식으로 표현해준다고함
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public  abstract class Item   {


    public final static String ALBUM = "음반";
    public final static String BOOK = "책";
    public final static String MOVIE = "영화";

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    @Column(name="dtype", insertable = false, updatable = false)
    protected String dtype;

    public String getDtype() {
        return dtype;
    }

    public String getDtypeNm() {
        String dtypeNm = null;

        if(dtype.equals("A")){
            dtypeNm = Item.ALBUM;
        }else if(dtype.equals("B")){
            dtypeNm = Item.BOOK;
        }else if(dtype.equals("M")){
            dtypeNm = Item.MOVIE;
        }
        return dtypeNm;
    }



    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    public void addStock(int quantity){
        this.stockQuantity +=quantity;
    }
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
    public abstract Item createItem(ItemForm itemForm);


    public abstract ItemForm transItemForm();
}