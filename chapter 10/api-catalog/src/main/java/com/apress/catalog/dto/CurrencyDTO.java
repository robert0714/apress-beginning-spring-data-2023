package com.apress.catalog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@EqualsAndHashCode(exclude= {"audit"})
public class CurrencyDTO {
    private Long id;
    private String code;
    private String description;
    private Boolean enable;
    private Integer decimalPlaces;

    private String symbol;

    private AuditDTO audit;

    private Long version;

	public CurrencyDTO() {}
    
    public CurrencyDTO(Long id, String code, String description, Boolean enable, Integer decimalPlaces) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.enable = enable;
        this.decimalPlaces = decimalPlaces;
    }

    // toString method
    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "id=" + getId().toString() +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", enable=" + enable +
                ", decimalPlaces=" + decimalPlaces +'}';
    }
    
}