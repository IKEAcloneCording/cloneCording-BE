package com.innovation.backend.entity;

import com.innovation.backend.dto.request.CartRequestDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="product_id")
    private Product product;

    @Column(nullable = false)
    private int count;//카트에 담긴 상품 개수


    //금액은 BigDecimal 사용하기
    @Column(nullable = false)
    private BigDecimal sum; //상품비용 합계

    @Column(nullable = false)
    private BigDecimal  delivery_fee; //배송비


public Cart (CartRequestDto cartRequestDto,Member member, Product product){
    this.product = product;
    this.member = member;
    this.count = cartRequestDto.getCount();
    this.sum =product.getPrice().multiply(new BigDecimal(count));
    this.delivery_fee = new BigDecimal(count * 15000);
}

    public void changeCount(int count){
        this.count = count;
    }
}
