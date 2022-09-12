package com.innovation.backend.dto.response;

import com.innovation.backend.entity.Cart;
import com.innovation.backend.entity.Product;
import lombok.Getter;

@Getter
public class CartResponseDto {

  private Product product;
  private Integer count;
  private Integer sum;
  private Integer delivery_fee;

  public CartResponseDto (Cart cart){
    this.product = cart.getProduct();
    this.count = cart.getCount();
    this.sum = cart.getSum();
    this.delivery_fee = count * sum ;

  }

}
