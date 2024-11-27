package com.apress.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "currency")
@org.springframework.data.relational.core.mapping.Table
public class Currency extends Base implements Serializable {

	@NotBlank(message = "Code is mandatory")
	@Column(name = "code", nullable = false, length = 4)
	private String code;

	@NotBlank(message = "Symbol is mandatory")
	@Column(name = "symbol", nullable = false, length = 4)
	private String symbol;

	@NotBlank(message = "Description is mandatory")
	@Column(name = "description", nullable = false, length = 30)
	private String description;
	@NotNull(message = "The state of the currency is mandatory")
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = Boolean.TRUE;

	@Min(value = 1, message = "The minimum value is 1")
	@Max(value = 5 , message = "The maximum value is 5")
	@Column(name = "decimal_places") //Optional: Indicate the name and the length of the column
	private int decimalPlaces;

	public Currency() {}
	
	public Currency(Long id, String code, String description, Boolean enabled, int decimalPlaces, String symbol) {
		super(id);
		this.code = code;
		this.description = description;
		this.enabled = enabled;
		this.decimalPlaces = decimalPlaces;
		this.symbol = symbol;
	}
	 
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Currency currency = (Currency) o;
		return decimalPlaces == currency.decimalPlaces && Objects.equals(code, currency.code) && Objects.equals(symbol, currency.symbol) && Objects.equals(description, currency.description) && Objects.equals(enabled, currency.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), code, symbol, description, enabled, decimalPlaces);
	}
	
	// toString method
    @Override
    public String toString() {
        return "Currency{" +
                "id=" + super.getId().toString() +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", decimalPlaces=" + decimalPlaces +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
