package com.keduit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {

    @NotNull(message = "상품 아이디 필수 입력")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상")
    private int count;


}
