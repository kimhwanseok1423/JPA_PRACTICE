package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.ItemForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter

public class Book extends Item{



    private String author;
    private String isbn;
    @Override
    public Item createItem(ItemForm itemForm) {

        this.setName(itemForm.getName());
        this.setPrice(itemForm.getPrice());
        this.setStockQuantity(itemForm.getStockQuantity());
        this.isbn = itemForm.getIsbn();
        this.author = itemForm.getAuthor();
        return this;
    }
    @Override
    public ItemForm transItemForm() {
        Book book = (Book) this;

        ItemForm itemForm = new ItemForm();
        itemForm.setDtype(book.transItemForm().getDtype());
        itemForm.setId(book.getId());
        itemForm.setName(book.getName());
        itemForm.setPrice(book.getPrice());
        itemForm.setStockQuantity(book.getStockQuantity());

        itemForm.setAuthor(book.getAuthor());
        itemForm.setIsbn(book.getIsbn());

        return itemForm;
    }
}
