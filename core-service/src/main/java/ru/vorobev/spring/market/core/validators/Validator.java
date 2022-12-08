package ru.vorobev.spring.market.core.validators;

public interface Validator<E> {
    void validate(E e);
}
