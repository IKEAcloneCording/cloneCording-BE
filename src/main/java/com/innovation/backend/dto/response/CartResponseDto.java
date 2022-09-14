package com.innovation.backend.dto.response;

import com.innovation.backend.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

  private Product product;
  private Long cart_id;
  private  int count;
  private  BigDecimal cart_price;// 카트 당 합계 금액
  private LocalDateTime createdAt;


//  public CartResponseDto (Cart cart){
//    this.cart_id = cart.getId();
//    this.count = cart.getCount();
//    this.sum = cart.getSum();
//    this.delivery_fee = new BigDecimal(count * 15000 );
//    this.createdAt = cart.getCreatedAt();
//
//    //productResponseDto 받기
//    for (Product product : cart.getProduct()){
//      ProductListResponseDto productListResponseDto = new ProductListResponseDto();
//      products.add(productListResponseDto);
//    }
//  }
}
