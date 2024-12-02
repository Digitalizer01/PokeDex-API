package com.pokedex.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Pokemon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name = "id_pokedex", nullable = false, unique = true)
	private int idPokedex;

	@NotNull
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "id_pokemon_type1")
	private PokemonType pokemonType1;

	@ManyToOne
	@JoinColumn(name = "id_pokemon_type2", nullable = true)
	private PokemonType pokemonType2;

	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull
	@OneToOne
	@JoinColumn(name = "id_stats", nullable = false)
	private Stats stats;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_generation", nullable = false)
	private Generation generation;

	@NotNull
	@Column(name = "legendary", nullable = false)
	private boolean legendary;

	public Pokemon() {

	}

	public Pokemon(@NotNull int idPokedex, @NotNull String name, @NotNull PokemonType pokemonType1, PokemonType pokemonType2,
			@NotNull String description, @NotNull Stats stats, @NotNull Generation generation,
			@NotNull boolean legendary) {
		this.idPokedex = idPokedex;
		this.name = name;
		this.pokemonType1 = pokemonType1;
		this.pokemonType2 = pokemonType2;
		this.description = description;
		this.stats = stats;
		this.generation = generation;
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

	public PokemonType getPokemonType1() {
		return pokemonType1;
	}

	public void setPokemonType1(PokemonType pokemonType1) {
		this.pokemonType1 = pokemonType1;
	}

	public PokemonType getPokemonType2() {
		return pokemonType2;
	}

	public void setPokemonType2(PokemonType pokemonType2) {
		this.pokemonType2 = pokemonType2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Generation getGeneration() {
		return generation;
	}

	public void setGeneration(Generation generation) {
		this.generation = generation;
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
		Pokemon other = (Pokemon) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (generation == null) {
			if (other.generation != null)
				return false;
		} else if (!generation.equals(other.generation))
			return false;
		if (id != other.id)
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
		if (pokemonType1 == null) {
			if (other.pokemonType1 != null)
				return false;
		} else if (!pokemonType1.equals(other.pokemonType1))
			return false;
		if (pokemonType2 == null) {
			if (other.pokemonType2 != null)
				return false;
		} else if (!pokemonType2.equals(other.pokemonType2))
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
		return "Pokemon [id=" + id + ", idPokedex=" + idPokedex + ", name=" + name + ", pokemonType1=" + pokemonType1
				+ ", pokemonType2=" + pokemonType2 + ", description=" + description + ", stats=" + stats
				+ ", generation=" + generation + ", legendary=" + legendary + "]";
	}

}
