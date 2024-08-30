package com.apress.catalog.dto;

import java.io.Serializable; 
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
@Data
public class CurrencyDTO implements Serializable {
    private Long id;
    private String code;
    private String description;
    private Boolean enable;
    private Integer decimalPlaces;

    private String symbol;

    private AuditDTO audit;

	public CurrencyDTO() {}
    
    public CurrencyDTO(Long id, String code, String description, Boolean enable, Integer decimalPlaces) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.enable = enable;
        this.decimalPlaces = decimalPlaces;
    }

	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE,true);
	}
}