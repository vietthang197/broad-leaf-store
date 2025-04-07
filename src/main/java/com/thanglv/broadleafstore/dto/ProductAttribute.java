package com.thanglv.broadleafstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductAttribute implements Serializable {
    private String name;
    private String value;
    private String label;
}
