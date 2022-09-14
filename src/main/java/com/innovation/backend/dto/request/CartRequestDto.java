package com.innovation.backend.dto.request;


import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "장바구니 상품 갯수 수정 요청 DTO")
@Getter
public class CartRequestDto {

  @NotNull
  @Schema(description = "상품Id", example = "1")
  private Long productId;

  @Schema(description = "원하는 상품 갯수", example = "2")
  private int count;

}
