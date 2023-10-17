package com.keduit.repository;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import com.keduit.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
// 통합 테스트를 위해 스프링부트에서 제공
@TestPropertySource(locations="classpath:application-test.properties")
// application.propertice 설정 값 보다 application-test.propertiesd의 설정이 더 우선순위 부여
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    public void forin(List<Item> itemList) {
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    public void createItemList() {
        for (int i = 1; i <= 20; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            if (i < 11){
                item.setItemSellStatus(ItemSellStatus.SELL);
                item.setStockNumber(100);
            } else {
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            }
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("조회 테스트")
    public void createItem() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("조회 테스트")
    public void itemNm() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품명 상세설명 테스트")
    public void findNmDetest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("less than 테스트")
    public void lessthan() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10003);
        forin(itemList);
    }

    @Test
    @DisplayName("desc 테스트")
    public void desctest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        forin(itemList);
    }

    @Test
    @DisplayName("query 테스트")
    public void querytest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        forin(itemList);
    }

    @Test
    @DisplayName("query native 테스트")
    public void nativetest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        forin(itemList);
    }

    @Test
    @DisplayName("select 테스트")
    public void testSelect() {
        Long id = 10L;
        Optional<Item> result = itemRepository.findById(id);
        System.out.println("===========");
        Item item = result.get();
        System.out.println("item = " + item);
    }

    @Transactional
    @Test
    @DisplayName("get1 테스트")
    public void testget() {
        this.createItemList();
        Long id = 10L;
        Item item = itemRepository.getOne(id);
        System.out.println("===========");
        System.out.println("item = " + item);
    }

    @Test
    public void testUpdate() {
        Item item = Item.builder().id(15L).itemNm("수정된 상품명").itemDetail("수정된 상세").price(25000).build();
        System.out.println("itemRepository.save(item) = " + itemRepository.save(item));
    }

    @Test
    public void testDelete() {
        Long id = 20L;
        itemRepository.deleteById(id);
    }

    @Test
    @DisplayName("페이지 처리")
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> result = itemRepository.findAll(pageable);
        System.out.println("result = " + result);
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 5, sort1);
        Page<Item> result = itemRepository.findAll(pageable);
        System.out.println("result = " + result);
//        for (Item item : result.getContent()){
//            System.out.println("item = " + item);
//        }
        result.get().forEach(item -> {
            System.out.println("item = " + item);
        });
    }

    @Test
    @DisplayName("dsl 테스트")
    public void quertdsl(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qitem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qitem).where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%")).orderBy(qitem.price.desc());

        List<Item> itemList = query.fetch();

        forin(itemList);
    }

    @Test
    @DisplayName("상품조회")
    public void queryD2(){
        this.createItemList();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // BooleanBuilder는 쿼리에 들어갈 조건을 만들어주는 빌더
        // predicate를 구현하고 있으며 메소드 체인 형식으로 사용 가능
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellstat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));

        booleanBuilder.and(item.price.gt(price));

        if (StringUtils.equals(itemSellstat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultitem: resultItemList){
            System.out.println(resultitem.toString());
        }
    }
}