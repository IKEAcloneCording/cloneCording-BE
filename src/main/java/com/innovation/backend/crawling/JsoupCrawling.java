package com.innovation.backend.crawling;

import com.innovation.backend.dto.response.ProductResponseDto;
import com.innovation.backend.entity.Product;
import com.innovation.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsoupCrawling {

    private final ProductRepository productRepository;

    @Transactional
    public List<ProductResponseDto> Crawling() throws IOException {
        List<ProductResponseDto> productList = new ArrayList<>();
        int page = 2;

        for(int j = 1; j<page; j++) {
            String IkeaUrl = "https://www.ikea.com/kr/ko/cat/desks-computer-desks-20649/?page=" + j;
            Document doc = Jsoup.connect(IkeaUrl).get();

            // product list
            Elements elements = doc.getElementsByAttributeValue("class", "plp-product-list__products");

            Element element = elements.get(1);
            Elements productsElements = element.getElementsByAttributeValue("class", "pip-product-compact");

            for (int i = 0; i < productsElements.size(); i++) {
                Element productElement = productsElements.get(i);
                Elements aElements = productElement.select("a");
                Element productLink = aElements.get(0);

                // 제품 링크(제품 상세 조회)
                String url = productLink.attr("href");

                // 제품 이미지
                Elements imgElements = productElement.select("img");
                String imageUrl = imgElements.attr("src"); // 제품 이미지 링크
                String subImageUrl = imgElements.attr("data-src"); // 제품 서브 이미지 링크

                // 제품명 및 가격
                Elements titleElements = productElement.getElementsByAttributeValue("class", "pip-header-section__title--small notranslate");
                String name = titleElements.text(); // 제품명
                Elements descriptionElements = productElement.getElementsByAttributeValue("class", "pip-header-section__description-text");
                String description = descriptionElements.text(); // 제품 상세 설명
                Elements measurementElements = productElement.getElementsByAttributeValue("class", "pip-header-section__description-measurement");
                String measurement = measurementElements.text(); // 제품 치수 설명
                Elements priceElements = productElement.getElementsByAttributeValue("class", "pip-price");
//                String productPrice = priceElements.text(); // 제품가격
                BigDecimal price;
                if (!priceElements.text().equals("")) {
                    String strProductPrice = priceElements.text().replaceAll("[^0-9]", "");
                    price = BigDecimal.valueOf(Integer.parseInt(strProductPrice));
                } else {
                    price = BigDecimal.valueOf(0);
                }
//                productList = new ArrayList<>();
//                for (Product product : productList) {
//                    productList.add(
                Product product = Product.builder()
                        .name(name)
                        .description(description)
                        .measurement(measurement)
                        .price(price)
                        .url(url)
                        .imageUrl(imageUrl)
                        .subImageUrl(subImageUrl)
                        .build();
//                    );
                productRepository.save(product);
                productList.add(
                        ProductResponseDto.builder()
                                .id(product.getId())
                                .name(name)
                                .description(description)
                                .measurement(measurement)
                                .price(price)
                                .url(url)
                                .image_url(imageUrl)
                                .subImage_url(subImageUrl)
                                .build()
                );
            }
        }
        return productList;
    }
}
