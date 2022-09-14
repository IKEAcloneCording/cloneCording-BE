package com.innovation.backend.service;


import com.innovation.backend.dto.response.CartGetListResponseDto;
import com.innovation.backend.dto.response.CartResponseDto;
import com.innovation.backend.dto.request.CartRequestDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import java.util.List;


public interface CartService {

  //장바구니에 상품 추가하기
  CartResponseDto addCart(CartRequestDto cartRequestDto,Member member, Product product);

  //장바구니 조회하기
  CartGetListResponseDto getCartList(Member member);

  //장바구니 상품 수량 변경
  CartResponseDto changeItemCount(Long id, Member member, CartRequestDto cartRequestDto);

  //장바구니 상품 삭제하기
  void deleteOneItem(Long id);

  //장바구니 전체상품 삭제하기
  void deleteAllItem(Member member);
}
