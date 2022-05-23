package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStackException;
import jpabook.jpashop.repository.OrderRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    private Book getBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member=new Member();
        member.setUsername("jji");
        member.setAddress(new Address("부천시","약대동","453398"));
        em.persist(member);
        return member;
    }

    @Test
    public void order() throws Exception{
        //given
        Member member = getMember();

        Book book = getBook("정의란 무엇인가", 15000,5);

        //when
        Long orderId= orderService.order(member.getId(), book.getId(), 5);

        //then
        Order getOrder=orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER,getOrder.getStatus());
        assertEquals(1,getOrder.getOrderItems().size());
        assertEquals(5,book.getStockQuantity());


    }



    @Test
    public void order_stockPlus() throws Exception{

        //given
        Member member=getMember();
        Item item = getBook("정의란 무엇인가", 15000,10);
        int orderCount=11;

        //when


        //then
        NotEnoughStackException thrown = assertThrows(NotEnoughStackException.class,()->orderService.order(member.getId(), item.getId(),orderCount));
        assertEquals("need more stock",thrown.getMessage());
    }


    @Test
    public void order_cancel() throws Exception {
        //given
        Member member=getMember();
        Item book = getBook("헬로우 월드",1000,10);

        int orderCount=2;
        Long orderId=orderService.order(member.getId(), book.getId(),orderCount);


        //when
       orderService.cancelOrder(orderId);



        //then
        Order getOrder= orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals(10,book.getStockQuantity());


    }



}