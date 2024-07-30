package jpabook.jpashop.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter

public class ItemDto {

    private long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String dtype;

    private String dtypeNm;
    public ItemDto(long id, String name, int price, int stockQuantity, String dtype, String artist, String etc, String author, String isbn, String director, String actor) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.dtype = dtype;
        this.artist = artist;
        this.etc = etc;
        this.author = author;
        this.isbn = isbn;
        this.director = director;
        this.actor = actor;
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

    private String artist;
    private String etc;
    private String author;
    private String isbn;
    private String director;
    private String actor;

}
