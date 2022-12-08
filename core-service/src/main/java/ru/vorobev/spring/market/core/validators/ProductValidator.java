package ru.vorobev.spring.market.core.validators;

import org.springframework.stereotype.Component;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.core.exceptions.ValidationException;
import ru.vorobev.spring.market.core.exceptions.ValidationFieldError;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator implements Validator<ProductDto> {
    @Override
    public void validate(ProductDto p) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (p.getPrice().doubleValue() < 0.01) {
            errors.add(new ValidationFieldError("price", p.getPrice().toString(), "Цена продукта не может быть меньше 1 коп."));
        }
        if (p.getTitle().length() < 3 || p.getTitle().length() > 255) {
            errors.add(new ValidationFieldError("title", p.getTitle(), "Длина названия продукта должна находиться в пределах 3-255 символов"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Продукт не прошел проверку", errors);
        }
    }
}
