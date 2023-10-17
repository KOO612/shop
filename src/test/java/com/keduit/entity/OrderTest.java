package com.keduit.entity;

import com.keduit.constant.ItemSellStatus;
import com.keduit.repository.ItemRepository;
import com.keduit.repository.MemberRepository;
import com.keduit.repository.OrderItemRepository;
import com.keduit.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    public Order createOrder(){
        Order order = new Order();

        for (int i =0; i < 3; i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            // 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담는다
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    @Test
    @DisplayName("영속성 테스트")
    public void casTest(){
        Order order = new Order();

        for (int i =0; i < 3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            // 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담는다
        }

        orderRepository.saveAndFlush(order);
        // 저장하고 강제로 flush를 호출 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영
        em.clear();
        // 초기화

        Order savedOrder = orderRepository.findById(order.getId())
                // 초기화 했기 때문에 주문 엔티티를 조회 select 쿼리 콘솔창에서 확인 가능
                .orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Test
    @DisplayName("고아객체 테스트")
    public void orphanTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        // order 엔티티에서 관리하고 있는 orderitem 리스트의 0번째 인덱스 요소 제거
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyTest(){
        Order order = this.createOrder();
        Long orderItemid = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemid).orElseThrow(EntityNotFoundException::new);
        System.out.println("order class : " + orderItem.getOrder().getClass());
    }

    @Test
    @DisplayName("지연 로딩 테스트 1")
    public void lazyTest1(){
        Order order = this.createOrder();
        Long orderItemid = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemid).orElseThrow(EntityNotFoundException::new);
        System.out.println("order class : " + orderItem.getOrder().getClass());
        System.out.println("==================");
        orderItem.getOrder().getOrderDate();
        System.out.println("==================");
    }
    // 출력 콘솔 이상함 확인 필요

}
