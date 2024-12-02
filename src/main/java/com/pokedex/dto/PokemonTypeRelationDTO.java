package com.pokedex.dto;

public class PokemonTypeRelationDTO {
	private String namePokemonType;
	private String nameRelatedPokemonType;
	private int effectivenessPercentage;

	public PokemonTypeRelationDTO() {

	}

	public PokemonTypeRelationDTO(String namePokemonType, String nameRelatedPokemonType, int effectivenessPercentage) {
		this.namePokemonType = namePokemonType;
		this.nameRelatedPokemonType = nameRelatedPokemonType;
		this.effectivenessPercentage = effectivenessPercentage;
	}

	public String getNamePokemonType() {
		return namePokemonType;
	}

	public void setNamePokemonType(String namePokemonType) {
		this.namePokemonType = namePokemonType;
	}

	public String getNameRelatedPokemonType() {
		return nameRelatedPokemonType;
	}

	public void setNameRelatedPokemonType(String nameRelatedPokemonType) {
		this.nameRelatedPokemonType = nameRelatedPokemonType;
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
		PokemonTypeRelationDTO other = (PokemonTypeRelationDTO) obj;
		if (effectivenessPercentage != other.effectivenessPercentage)
			return false;
		if (namePokemonType == null) {
			if (other.namePokemonType != null)
				return false;
		} else if (!namePokemonType.equals(other.namePokemonType))
			return false;
		if (nameRelatedPokemonType == null) {
			if (other.nameRelatedPokemonType != null)
				return false;
		} else if (!nameRelatedPokemonType.equals(other.nameRelatedPokemonType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PokemonTypeRelationDTO [namePokemonType=" + namePokemonType + ", nameRelatedPokemonType="
				+ nameRelatedPokemonType + ", effectivenessPercentage=" + effectivenessPercentage + "]";
	}

}
