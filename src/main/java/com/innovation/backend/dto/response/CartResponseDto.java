package com.innovation.backend.dto.response;

import com.innovation.backend.entity.Cart;
import com.innovation.backend.entity.Product;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CartResponseDto {

  private final Product product;
  private final Integer count;
  private final BigDecimal sum;
  private final BigDecimal delivery_fee;

  public CartResponseDto (Cart cart){
    this.product = cart.getProduct();
    this.count = cart.getCount();
    this.sum = cart.getSum();
    this.delivery_fee = new BigDecimal(count * 15000 );

  }

}
