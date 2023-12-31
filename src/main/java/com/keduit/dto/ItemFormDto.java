package com.keduit.dto;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명 필수")
    private String itemNm;

    @NotNull(message = "가격 필수")
    private Integer price;

    @NotBlank(message = "이름 필수")
    private String itemDetail;

    @NotNull(message = "재고 필수")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    // 상품 저장 후 수정할 때 상품 이미지 정보 저장 리스트

    private List<Long> itemImgIds = new ArrayList<>();
    // 상품의 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    // modelMapper로 엔티티 객체와 dto 객체 간의 데이터를 복사해 복사한 객체를 반환하는 메소드

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
    // modelMapper로 엔티티 객체와 dto 객체 간의 데이터를 복사해 복사한 객체를 반환하는 메소드
}
