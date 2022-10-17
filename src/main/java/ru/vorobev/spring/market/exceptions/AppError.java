package ru.vorobev.spring.market.exceptions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private int statusCode;
    private String message;
}
