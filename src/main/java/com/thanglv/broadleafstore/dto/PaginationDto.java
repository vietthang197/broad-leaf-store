package com.thanglv.broadleafstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationDto <T> {
    private List<T> content;
    private PageInfo pageInfo;

    @Getter
    @Setter
    public static class PageInfo {
        private Integer number;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
    }
}
