package com.innovation.backend.service;


import com.innovation.backend.dto.response.CartResponseDto;
import com.innovation.backend.dto.resquest.CartRequestDto;
import com.innovation.backend.entity.Cart;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.repository.CartRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Repository
public class CartServiceImpl implements CartService{

  private final CartRepository cartRepository;


  //장바구니에 상품 추가하기
  @Transactional
  @Override
  public CartResponseDto addCart(CartRequestDto cartRequestDto,Member member, Product product){
    List<Cart> cartList = cartRepository.findByMemberAndProduct(member, product);
    Cart cart;
    //장바구니에 상품이 없을 때
    if (cartList.isEmpty()) {
      cart = new Cart(cartRequestDto,member,product);
      cart = cartRepository.save(cart);
    } else {//장바구니에 상품이 있을 때
      cart = cartList.get(0);
      cart.changeCount(cart.getCount()+1);
    }
    return new CartResponseDto(cart);
  }



  //장바구니 조회하기
  @Override
  public List<CartResponseDto> getCartList(Member member) {
    List<Cart> carts = cartRepository.findByMember(member);

    return carts.stream().map(CartResponseDto::new).collect(Collectors.toList()); // 람다식
//TODO: CartResponseDto를 바꾸고 나서 수정하기

//    List<CartResponseDto> cartResponseDtoList = new ArrayList<>();
//    for(Cart cart : carts){
//      CartResponseDto cartResponseDto = new CartResponseDto(cart);
//      cartResponseDtoList.add(cartResponseDto);
//    }
//
//    return cartResponseDtoList;
  }


  //장바구니 상품 수량 변경
  @Transactional
  @Override
  public CartResponseDto changeItemCount(Long id, Member member, CartRequestDto cartRequestDto) {
    Cart cart = cartRepository.findByIdAndMember(id,member)//cart_id
        .orElseThrow(EntityNotFoundException::new);
//        .orElseThrow(() -> new Exception (ErrorCode.ENTITY_NOT_FOUND));
    cart.changeCount(cartRequestDto.getCount());
    return new CartResponseDto(cart);
  }

  //장바구니 상품 삭제하기
  @Transactional
  @Override
  public void deleteOneItem(Long id) { //cart_id
    cartRepository.deleteById(id);
  }

  //장바구니 전체상품 삭제하기
  @Transactional
  @Override
  public void deleteAllItem(Member member) {
    cartRepository.deleteByMember(member);
  }
}