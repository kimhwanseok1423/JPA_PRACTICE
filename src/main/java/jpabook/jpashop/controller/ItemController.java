package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.NotHasDiscriminator;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class ItemController {

private final ItemService itemService;
@GetMapping("/items/new")
public String createForm(Model model){
    model.addAttribute("itemForm",new ItemForm());
    return "items/mixItemForm";

}@PostMapping("/items/new")
    public String create(@Valid ItemForm itemForm, BindingResult result, Model model){
        if(result.hasErrors()){
            return "items/mixItemForm";
        }

        Item item = null;
        if("A".equals(itemForm.getDtype())){
            item=new Album().createItem(itemForm);
        } else if("B".equals(itemForm.getDtype())){
            item=new Book().createItem(itemForm);
        }else if("M".equals(itemForm.getDtype())) {
            item = new Movie().createItem(itemForm);
        } else{
            throw new NotHasDiscriminator("Not Has Discriminator");
        }
itemService.saveItem(item);
return "redirect:/items";

    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.finditems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit" )
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findOne(itemId);

        ItemForm itemForm = item.transItemForm();

        model.addAttribute("itemForm", itemForm);

        return "items/editItemForm";
    }
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm itemForm){

        Item item = itemService.transItemEntity(itemForm);
        //itemService.saveItem(item);
        itemService.saveItem(item);

        return "redirect:items";

    }
}
