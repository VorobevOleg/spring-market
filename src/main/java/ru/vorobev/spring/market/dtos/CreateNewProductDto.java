package ru.vorobev.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateNewProductDto {
    private String title;
    private BigDecimal price;
}
