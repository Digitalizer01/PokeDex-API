package com.pokedex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pokedex.model.PokemonTypeRelation;

@Repository
public interface PokemonTypeRelationRepository extends JpaRepository<PokemonTypeRelation, Integer> {
	
	boolean existsByPokemonTypeNameAndRelatedPokemonTypeName(String pokemonTypeName, String relatedPokemonTypeName);

	int findPokemonTypeRelationEffectiveness_percentageByPokemonTypeIdAndRelatedPokemonTypeId(int idtype,
			int idrelatedtype);

	PokemonTypeRelation findPokemonTypeRelationEffectiveness_percentageByPokemonTypeNameAndRelatedPokemonTypeName(
			String namePokemonType, String nameRelatedPokemonType);

	List<PokemonTypeRelation> findAllByPokemonTypeNameAndEffectivenessPercentage(String nameType,
			int effectivenessPercentage);

	PokemonTypeRelation findPokemonTypeRelationById(int id);

}
