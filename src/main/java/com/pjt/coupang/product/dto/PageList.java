package com.pjt.coupang.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageList<T> {

    List<T> list;
    boolean hasNext;

    public PageList(List<T> list, boolean hasNext) {
        this.list = list;
        this.hasNext = hasNext;
    }
}
