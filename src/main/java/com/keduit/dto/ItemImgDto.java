package com.keduit.dto;

import com.keduit.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemImgDto {
    // 상품 저장 후 상품 이미지에 대한 데이터를 전달할 dto 클래스

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg,ItemImgDto.class);
        // itemimg 엔티티 객체를 파라미터로 받아서 itemimg 객체의 자료형과 멤버변수의 이름이 같을 때 itemimgdto로 값을 복사해서 반환
        // static 메소드로 선언해 itemimgdto 객체를 생성하지 않아도 호출 가능하도록
    }
}
