package com.apress.catalog.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@EqualsAndHashCode(exclude= {"country"})
public class StateDTO implements Serializable {
	private Long id;

	private String code;
	private String name;
	private Boolean enabled;

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
