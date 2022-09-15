package com.innovation.backend.controller;

import com.innovation.backend.dto.response.CartGetListResponseDto;
import com.innovation.backend.dto.response.CartResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.dto.request.CartRequestDto;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.repository.ProductRepository;
import com.innovation.backend.security.user.UserDetailsImpl;
import com.innovation.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


@Tag(name="[장바구니 API]")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CartController {

  private final CartService cartService;
  private final ProductRepository productRepository;

  //장바구니 상품 추가
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "상품추가 성공", content = @Content(schema = @Schema(implementation = CartResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
  @Operation(summary = "장바구니 상품 추가", description = "장바구니에 없는 상품이면 신규 추가, 기존에 존재하던 상품이면 갯수를 추가합니다.")
  @PostMapping("/api/auth/cart")
  public ResponseDto<CartResponseDto> addCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartRequestDto cartRequestDto) {
    CartResponseDto cartResponseDto;
    try {
      Member member = userDetails.getMember();
      Product product = productRepository.findById(cartRequestDto.getProductId())
          .orElseThrow(EntityNotFoundException::new);
      cartResponseDto = cartService.addCart(cartRequestDto,member,product);
    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.ENTITY_NOT_FOUND);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success(cartResponseDto);
  }


  //장바구니 조회
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "상품조회 성공", content = @Content(schema = @Schema(implementation = CartGetListResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
  @Operation(summary = "장바구니 조회", description = "장바구니에 넣은 모든 상품과 총 합산금액을 조회합니다.")
  @GetMapping("/api/auth/cart")
  public ResponseDto<CartGetListResponseDto> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
    CartGetListResponseDto cartGetListResponseDto;
    try {
      Member member = userDetails.getMember();
      cartGetListResponseDto = cartService.getCartList(member);
    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.ENTITY_NOT_FOUND);

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success(cartGetListResponseDto);
  }



  //장바구니 상품 수량 변경
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "상품 수량 변경 성공", content = @Content(schema = @Schema(implementation = CartResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
  @Operation(summary = "장바구니 상품 수량 변경", description = "cart_id 를 통해 해당 상품의 갯수 (count)를 변경합니다.")
  @Parameter(name = "id", description = "cart 의 id", in = ParameterIn.PATH)
  @PutMapping("/api/auth/cart/{id}/change-count")
  public ResponseDto<CartResponseDto>  changeCount (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartRequestDto cartRequestDto ){
    CartResponseDto cartResponseDto;
    try {
      Member member = userDetails.getMember();
      cartResponseDto = cartService.changeItemCount(id,member,cartRequestDto);
    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.ENTITY_NOT_FOUND);

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success(cartResponseDto);
  }


  //장바구니 상품 삭제
  @Operation(summary = "장바구니 상품 하나 삭제", description = "장바구니에 있는 상품 중 하나만 삭제합니다.")
  @Parameter(name = "id", description = "cart 의 id", in = ParameterIn.PATH)
@DeleteMapping("/api/auth/cart/{id}")
public ResponseDto<String> deleteOneItem (@PathVariable Long id){
  try {
    cartService.deleteOneItem(id);
  } catch (EntityNotFoundException e) {
    log.error(e.getMessage());
    return ResponseDto.fail(ErrorCode.ENTITY_NOT_FOUND);

  } catch (Exception e) {
    log.error(e.getMessage());
    return ResponseDto.fail(ErrorCode.INVALID_ERROR);
  }
  return ResponseDto.success("success");
}

  //장바구니 상품 전체 삭제
  @Operation(summary = "장바구니 상품 전체 삭제", description = "장바구니에 있는 모든 상품을 삭제합니다.")
  @DeleteMapping("/api/auth/cart")
  public ResponseDto<String> deleteAllItem (@AuthenticationPrincipal UserDetailsImpl userDetails){
    try {
      Member member = userDetails.getMember();
      cartService.deleteAllItem(member);

    } catch (EntityNotFoundException e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.ENTITY_NOT_FOUND);

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseDto.fail(ErrorCode.INVALID_ERROR);
    }
    return ResponseDto.success("success");
  }
}
