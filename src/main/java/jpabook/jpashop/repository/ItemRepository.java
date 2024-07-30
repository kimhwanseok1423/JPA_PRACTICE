package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null) {
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);

    }

    public List<Item> findAll (){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
    public List<ItemDto> findListAll(){
        String ItemDto= "jpabook.jpashop.domain.item.ItemDto";
        List<ItemDto> resultList = em.createQuery("select new "+ItemDto+"(i.id, i.name, i.price, i.stockQuantity, i.dtype, TREAT(i AS Album ).artist, TREAT(i AS Album ).etc, TREAT(i AS Book ).author, TREAT(i AS Book ).isbn, TREAT(i AS Movie ).director, TREAT(i AS Movie ).actor) from Item i", ItemDto.class)
                .getResultList();

        return resultList;
    }


}
