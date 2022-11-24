package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.core.entities.Category;
import ru.vorobev.spring.market.core.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
