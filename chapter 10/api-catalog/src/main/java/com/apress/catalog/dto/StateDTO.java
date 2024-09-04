package com.apress.catalog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@EqualsAndHashCode(exclude= {"country"})
public class StateDTO {
	private Long id;

	private String code;
	private String name;
	private Boolean enabled;

	private Long version;
	private CountryDTO country;

	public StateDTO() {}

	public StateDTO(Long id, String code, String name, Boolean enabled, CountryDTO country) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.enabled = enabled;
		this.country = country;
	}
}
