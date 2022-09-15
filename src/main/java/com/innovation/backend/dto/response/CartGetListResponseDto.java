package com.innovation.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "장바구니 전체 조회 응답DTO")
@Getter
@Builder
public class CartGetListResponseDto {

  @Schema(description = "장바구니 상품들")
  private List<CartResponseDto> cartProducts;
  @Schema(description = "총 배송비", example = "15000")
  private BigDecimal total_delivery_fee;
  @Schema(description = "총 제품비", example = "229000")
  private BigDecimal total_order_price;
  @Schema(description = "총 금액 (배송비+제품비)", example = "244000")
  private BigDecimal total_order_and_delivery_price;


}
