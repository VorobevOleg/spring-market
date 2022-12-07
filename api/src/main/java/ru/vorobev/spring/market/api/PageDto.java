package ru.vorobev.spring.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Модель страницы с объектами")
public class PageDto<E> {
    @Schema(description = "Список объектов", required = true)
    private List<E> items;
    @Schema(description = "Номер страницы", required = true, example = "1")
    private int page;
    @Schema(description = "Количество страниц", required = true, example = "10")
    private int totalPages;

    public PageDto() {
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
