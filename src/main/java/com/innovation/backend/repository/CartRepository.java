package com.innovation.backend.repository;


import com.innovation.backend.entity.Cart;
import com.innovation.backend.entity.Member;
import com.innovation.backend.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

  List<Cart> findByMember(Member member);

  Optional<Cart> findByIdAndMember(Long id, Member member);

  void deleteByMember(Member member);

  List<Cart> findByMemberAndProduct(Member member, Product product);

}