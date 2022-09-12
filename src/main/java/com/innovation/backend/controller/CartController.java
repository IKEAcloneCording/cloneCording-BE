package com.innovation.backend.controller;

import com.innovation.backend.dto.response.CartResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.dto.resquest.CartRequestDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.service.CartService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartController {

  private final CartService cartService;

  //장바구니 상품 추가
  @PostMapping("/api/cart")
  public ResponseDto<CartResponseDto> addCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartRequestDto cartRequestDto) {
    CartResponseDto cartResponseDto;
    try {
      Member member = userDetails.getMember();
//product.productRepository.find웅앵
      cartResponseDto = cartService.addCart(cartRequestDto, member,product);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);

    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success(cartResponseDto);
  }


  //장바구니 조회
  @GetMapping("/api/cart")
  public ResponseDto<List<CartResponseDto>> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
    List<CartResponseDto> cartResponseDtoList;
    try {
      Member member = userDetails.getMember();
      cartResponseDtoList = cartService.getCartList(member);

    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    }
    return ResponseDto.success(cartResponseDtoList);
  }



  //장바구니 상품 수량 변경
  @PutMapping("/api/cart/{id}/change-count")
  public ResponseDto<CartResponseDto>  changeCount (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartRequestDto cartRequestDto ){
    CartResponseDto cartResponseDto;
    try {
      Member member = userDetails.getMember();
      cartResponseDto = cartService.changeItemCount(id,member,cartRequestDto);
    }catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseDto<>(null, ErrorCode.ENTITY_NOT_FOUND);
    }
    return ResponseDto.success(cartResponseDto);
  }


  //장바구니 상품 삭제
@DeleteMapping("/api/cart/{id}")
public ResponseDto<String> deleteOneItem (Long id){
  try {
    cartService.deleteOneItem(id);
  }catch (EntityNotFoundException e){
    log.error(e.getMessage());
    return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
  }catch (Exception e){
    log.error(e.getMessage());
    return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
  }
  return ResponseDto.success("success");
}

  //장바구니 상품 전체 삭제
  @DeleteMapping("/api/cart")
  public ResponseDto<String> deleteAllItem (@AuthenticationPrincipal UserDetailsImpl userDetails){
    try {
      Member member = userDetails.getMember();
      cartService.deleteAllItem(member);
    }catch (EntityNotFoundException e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.ENTITY_NOT_FOUND);
    }catch (Exception e){
      log.error(e.getMessage());
      return new ResponseDto<>(null,ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success("success");
  }
}
