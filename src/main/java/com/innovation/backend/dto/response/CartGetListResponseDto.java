package com.innovation.backend.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartGetListResponseDto {

  private List<CartResponseDto> cartProducts;
  private BigDecimal total_delivery_fee;
  private BigDecimal total_order_price;


}
