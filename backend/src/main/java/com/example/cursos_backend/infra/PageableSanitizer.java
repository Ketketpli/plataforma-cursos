package com.example.cursos_backend.infra;

import com.example.cursos_backend.exceptions.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PageableSanitizer {

    public static Pageable validate(Pageable pageable, Set<String> allowedFields) {

        if(pageable.getSort().isUnsorted()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
        }

        for(Sort.Order order : pageable.getSort()){
            String field = order.getProperty();

            if(!allowedFields.contains(field)) {
                throw new BadRequestException();
            }
        }

        return pageable;
    }
}
