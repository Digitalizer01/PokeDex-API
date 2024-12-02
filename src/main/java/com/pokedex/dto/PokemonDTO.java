package com.pokedex.dto;

public class PokemonDTO {
	private int id;
	private int idPokedex;
	private String name;
	private String namePokemonType1;
	private String namePokemonType2; // Can be null
	private String description;
	private StatsDTO stats;
	private int idGeneration;
	private boolean legendary;

	public PokemonDTO() {

	}

	public PokemonDTO(int idPokedex, String name, String namePokemonType1, String namePokemonType2, String description,
			StatsDTO stats, int idGeneration, boolean legendary) {
		this.idPokedex = idPokedex;
		this.name = name;
		this.namePokemonType1 = namePokemonType1;
		this.namePokemonType2 = namePokemonType2;
		this.description = description;
		this.stats = stats;
		this.idGeneration = idGeneration;
		this.legendary = legendary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPokedex() {
		return idPokedex;
	}

	public void setIdPokedex(int idPokedex) {
		this.idPokedex = idPokedex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePokemonType1() {
		return namePokemonType1;
	}

	public void setNamePokemonType1(String namePokemonType1) {
		this.namePokemonType1 = namePokemonType1;
	}

	public String getNamePokemonType2() {
		return namePokemonType2;
	}

	public void setNamePokemonType2(String namePokemonType2) {
		this.namePokemonType2 = namePokemonType2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatsDTO getStats() {
		return stats;
	}

	public void setStats(StatsDTO stats) {
		this.stats = stats;
	}

	public int getIdGeneration() {
		return idGeneration;
	}

	public void setIdGeneration(int idGeneration) {
		this.idGeneration = idGeneration;
	}

	public boolean isLegendary() {
		return legendary;
	}

	public void setLegendary(boolean legendary) {
		this.legendary = legendary;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonDTO other = (PokemonDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (idGeneration != other.idGeneration)
			return false;
		if (idPokedex != other.idPokedex)
			return false;
		if (legendary != other.legendary)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (namePokemonType1 == null) {
			if (other.namePokemonType1 != null)
				return false;
		} else if (!namePokemonType1.equals(other.namePokemonType1))
			return false;
		if (namePokemonType2 == null) {
			if (other.namePokemonType2 != null)
				return false;
		} else if (!namePokemonType2.equals(other.namePokemonType2))
			return false;
		if (stats == null) {
			if (other.stats != null)
				return false;
		} else if (!stats.equals(other.stats))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", idPokedex=" + idPokedex + ", name=" + name + ", namePokemonType1="
				+ namePokemonType1 + ", namePokemonType2=" + namePokemonType2 + ", description=" + description
				+ ", stats=" + stats + ", idGeneration=" + idGeneration + ", legendary=" + legendary + "]";
	}

}
