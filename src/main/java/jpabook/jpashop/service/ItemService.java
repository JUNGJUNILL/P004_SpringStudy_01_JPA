package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
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
    public void save(Item item){
        itemRepository.save(item);
    }


    //merge는 모든 필드의값을 변경한다. 그러므로 파라메터가 안넘오 올 시 null로 처리 될 수 있다.
    //그러므로 merge말고 변경 감지를 쓰도록 하자.
    //변경 감지 기능 사용
    @Transactional
    public void updateItem(Long itemId, UpdateItemDto itemDto){
        Item findItem = itemRepository.findOne(itemId); //영속 상태
        //트렌젝션 안에서 조회를 해야 영속성으로 조회가 되고
        //거기에 값을 변경 해야 변경 감지가 되면서 update가 쳐진다.
        
        findItem.setName(itemDto.getName());
        findItem.setPrice(itemDto.getPrice());
        findItem.setStockQuantity(itemDto.getStockQuantity());

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
