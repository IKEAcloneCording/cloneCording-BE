package com.innovation.backend.dto.request;


import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class CartRequestDto {

  @NotNull
  private Long productId;

  private int count;

}
