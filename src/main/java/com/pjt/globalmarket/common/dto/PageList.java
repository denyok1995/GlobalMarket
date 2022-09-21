package com.pjt.globalmarket.common.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class PageList<T> {

    private List<T> content;

    private long totalPages;

    private long totalElements;

    public static PageList toDto(List content, long totalPages, long totalElements) {
        return PageList.builder().content(content).totalPages(totalPages).totalElements(totalElements).build();
    }

}
