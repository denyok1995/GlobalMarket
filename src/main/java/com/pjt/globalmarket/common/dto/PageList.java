package com.pjt.globalmarket.common.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageList<T> {

    private List<T> content;

    private long totalPages;

    private long totalElements;

}
