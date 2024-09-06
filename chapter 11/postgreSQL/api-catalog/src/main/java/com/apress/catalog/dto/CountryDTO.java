package com.apress.catalog.dto;


import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@EqualsAndHashCode(exclude= {"currency"})
public class CountryDTO implements Serializable {
	private Long id;
	private String code;
	private String name;
	private String locale;
	private String timezone;
	private Boolean enabled;

	private CurrencyDTO currency;

	private List<StateDTO> states;

	public CountryDTO() {}

	public CountryDTO(Long id, String code, String name, String locale, String timezone, Boolean enabled, CurrencyDTO currency) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.locale = locale;
		this.timezone = timezone;
		this.enabled = enabled;
		this.currency = currency;
	}
}
