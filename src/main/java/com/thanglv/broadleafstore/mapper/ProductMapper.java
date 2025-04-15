package com.thanglv.broadleafstore.mapper;

import com.thanglv.broadleafstore.dto.ProductDto;
import com.thanglv.broadleafstore.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
}
