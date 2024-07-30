package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.ItemForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item{

    private String director;
    private String actor;


    @Override
      public Item createItem(ItemForm itemForm) {

        this.setName(itemForm.getName());
        this.setPrice(itemForm.getPrice());
        this.setStockQuantity(itemForm.getStockQuantity());
        this.director = itemForm.getDirector();
        this.actor = itemForm.getActor();
        return this;
    }
    @Override
    public ItemForm transItemForm() {
        Movie movie = (Movie) this;
        ItemForm itemForm = new ItemForm();
        itemForm.setDtype(movie.transItemForm().getDtype());
        itemForm.setId(movie.getId());
        itemForm.setName(movie.getName());
        itemForm.setPrice(movie.getPrice());
        itemForm.setStockQuantity(movie.getStockQuantity());

        itemForm.setDirector(movie.getDirector());
        itemForm.setActor(movie.getActor());

        return itemForm;
    }
}
