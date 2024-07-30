package jpabook.jpashop.service;

import jpabook.jpashop.controller.ItemForm;
import jpabook.jpashop.domain.item.*;
import jpabook.jpashop.exception.NotHasDiscriminator;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
@Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> finditems(){
    return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
    return itemRepository.findOne(itemId);
    }
    public List<ItemDto> findItemDtoList(){
        return itemRepository.findListAll();
    }
    public Item transItemEntity(ItemForm itemForm) {
        Item item = null;
        String dtype = itemForm.getDtype();

        if("A".equals(itemForm.getDtype())){
            item = new Album().createItem(itemForm); // 앨범 생성
        }else if("B".equals(itemForm.getDtype())){
            item = new Book().createItem(itemForm); // 책 생성
        }else if("M".equals(itemForm.getDtype())){
            item = new Movie().createItem(itemForm);  // 영화 생성
        }else{
            throw new NotHasDiscriminator("Not Has Discriminator");
        }

        return item;
    }

}
