package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.ItemForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@DiscriminatorValue("A")
@Entity
@Getter
@Setter

public class Album extends Item{

    private String artist;
    private String etc;


    @Override
    public Item createItem(ItemForm itemForm) {
        this.setName(itemForm.getName());
        this.setPrice(itemForm.getPrice());
        this.setStockQuantity(itemForm.getStockQuantity());
        this.setArtist(itemForm.getArtist());
        this.setEtc(itemForm.getEtc());
        return this;
    }
    @Override
    public ItemForm transItemForm() {
        Album album = (Album) this;

        ItemForm itemForm = new ItemForm();
        itemForm.setDtype(album.getDtype());
        itemForm.setId(album.getId());
        itemForm.setName(album.getName());
        itemForm.setPrice(album.getPrice());
        itemForm.setStockQuantity(album.getStockQuantity());

        itemForm.setArtist(album.getArtist());
        itemForm.setEtc(album.getEtc());

        return itemForm;
    }
}
