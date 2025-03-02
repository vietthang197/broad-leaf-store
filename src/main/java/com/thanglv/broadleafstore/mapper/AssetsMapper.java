package com.thanglv.broadleafstore.mapper;

import com.thanglv.broadleafstore.dto.AssetsDto;
import com.thanglv.broadleafstore.entity.Asset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetsMapper {

    AssetsDto toDto(Asset asset);
}
