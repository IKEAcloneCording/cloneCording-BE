package com.innovation.backend.dto.response;

import com.innovation.backend.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "장바구니 응답DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
  @Schema(description = "상품")
  private Product product;
  @Schema(description = "카트 아이디")
  private Long cart_id;
  @Schema(description = "상품 갯수")
  private  int count;
  @Schema(description = "카트의 합계 금액")
  private  BigDecimal cart_price;// 카트 당 합계 금액
  @Schema(description = "일자")
  private LocalDateTime createdAt;

}
