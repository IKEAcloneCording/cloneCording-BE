package com.innovation.backend.dto.resquest;


import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class CartRequestDto {

  @NotNull
  private Long productId;

  private int count;

}
