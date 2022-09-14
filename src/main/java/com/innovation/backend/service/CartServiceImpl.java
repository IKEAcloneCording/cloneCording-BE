package com.innovation.backend.service;


import com.innovation.backend.dto.response.CartGetListResponseDto;
import com.innovation.backend.dto.response.CartResponseDto;
import com.innovation.backend.dto.request.CartRequestDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Cart;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import com.innovation.backend.repository.CartRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    cartRepository.save(cart);
    return CartResponseDto.builder()
        .cart_id(cart.getId())
        .product(cart.getProduct())
        .count(cartRequestDto.getCount())
        .cart_price(cart.getSum())
  //      .delivery_fee(new BigDecimal(cartRequestDto.getCount() * 15000))
        .createdAt(cart.getCreatedAt())
        .build();
  }


  //장바구니 조회하기
  @Override
  public CartGetListResponseDto getCartList(Member member) {
    List<Cart> carts = cartRepository.findByMember(member);
//
//    List<CartResponseDto> cartResponseDtoList = new ArrayList<>();
//    for(Cart cart : carts){
//      cartResponseDtoList.add(
//          CartResponseDto.builder()
//              .cart_id(cart.getId())
//              //.productList(List<cart.getProduct()>)
//              .count(cart.getCount())
//              .sum(cart.getSum())
//              .delivery_fee(cart.getDelivery_fee())
//              .createdAt(cart.getCreatedAt())
//              .build());
//    }
//       return cartResponseDtoList;
//
//  }

    List<CartResponseDto> cartProducts = new ArrayList<>();

    int totalCnt = 0;
    BigDecimal totalPrice = new BigDecimal(0);
    for (Cart cart : carts) {
      cartProducts.add(
          CartResponseDto.builder()
              .cart_id(cart.getId())
              .product(cart.getProduct())
              .count(cart.getCount())
              .cart_price(cart.getProduct().getPrice().multiply(new BigDecimal(cart.getCount())))
              .createdAt(cart.getCreatedAt())
              .build());
      totalCnt += cart.getCount();
      totalPrice = totalPrice.add(cart.getProduct().getPrice().multiply(new BigDecimal(cart.getCount())));
    }

    CartGetListResponseDto cartGetListResponseDto = CartGetListResponseDto.builder()
        .cartProducts(cartProducts)
        .total_delivery_fee(new BigDecimal(15000 * totalCnt))
        .total_order_price(totalPrice)
        .total_order_and_delivery_price(totalPrice.add(new BigDecimal(15000 * totalCnt)))
        .build();

    return cartGetListResponseDto;
  }


  //장바구니 상품 수량 변경
  @Transactional
  @Override
  public CartResponseDto changeItemCount(Long id, Member member, CartRequestDto cartRequestDto) {
    Cart cart = cartRepository.findByIdAndMember(id,member)//cart_id
        .orElseThrow(EntityNotFoundException::new);
    cart.changeCount(cartRequestDto.getCount());
    cartRepository.save(cart);
    return CartResponseDto.builder()
        .cart_id(cart.getId())
        .product(cart.getProduct())
        .count(cartRequestDto.getCount())
        .cart_price(cart.getSum())
  //      .delivery_fee(new BigDecimal(cartRequestDto.getCount() * 15000))
        .createdAt(cart.getCreatedAt())
        .build();
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