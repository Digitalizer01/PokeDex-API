package com.pokedex.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class PokemonTypeRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_pokemon_type", nullable = true)
	private PokemonType pokemonType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_pokemon_related_type", nullable = true)
	private PokemonType relatedPokemonType;

	@NotNull
	@Column(name = "effectiveness_percentage", nullable = false)
	private int effectivenessPercentage;

	public PokemonTypeRelation() {

	}

	public PokemonTypeRelation(@NotNull PokemonType pokemonType, @NotNull PokemonType relatedPokemonType,
			@NotNull int effectivenessPercentage) {
		this.pokemonType = pokemonType;
		this.relatedPokemonType = relatedPokemonType;
		this.effectivenessPercentage = effectivenessPercentage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PokemonType getPokemonType() {
		return pokemonType;
	}

	public void setPokemonType(PokemonType pokemonType) {
		this.pokemonType = pokemonType;
	}

	public PokemonType getRelatedPokemonType() {
		return relatedPokemonType;
	}

	public void setRelatedPokemonType(PokemonType relatedPokemonType) {
		this.relatedPokemonType = relatedPokemonType;
	}

	public int getEffectivenessPercentage() {
		return effectivenessPercentage;
	}

	public void setEffectivenessPercentage(int effectivenessPercentage) {
		this.effectivenessPercentage = effectivenessPercentage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonTypeRelation other = (PokemonTypeRelation) obj;
		if (effectivenessPercentage != other.effectivenessPercentage)
			return false;
		if (id != other.id)
			return false;
		if (pokemonType == null) {
			if (other.pokemonType != null)
				return false;
		} else if (!pokemonType.equals(other.pokemonType))
			return false;
		if (relatedPokemonType == null) {
			if (other.relatedPokemonType != null)
				return false;
		} else if (!relatedPokemonType.equals(other.relatedPokemonType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PokemonTypeRelation [id=" + id + ", pokemonType=" + pokemonType + ", relatedPokemonType="
				+ relatedPokemonType + ", effectivenessPercentage=" + effectivenessPercentage + "]";
	}

}
