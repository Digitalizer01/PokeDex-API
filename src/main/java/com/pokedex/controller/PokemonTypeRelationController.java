package com.pokedex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pokedex.dto.PokemonTypeRelationDTO;
import com.pokedex.model.PokemonTypeRelation;
import com.pokedex.service.PokemonTypeRelationService;

@RestController
@RequestMapping("/pokemontyperelation")
public class PokemonTypeRelationController {

	@Autowired
	private PokemonTypeRelationService pokemonTypeRelationService;

	@PostMapping
	public ResponseEntity<PokemonTypeRelation> addPokemonTypeRelation(
			@RequestBody PokemonTypeRelationDTO pokemonTypeRelationDTO) {
		PokemonTypeRelation pokemonTypeRelation = pokemonTypeRelationService
				.addPokemonTypeRelation(pokemonTypeRelationDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemonTypeRelation);
	}
	
	@PostMapping("/batch")
	public ResponseEntity<List<PokemonTypeRelation>> addPokemonTypeRelationList(@RequestBody List<PokemonTypeRelationDTO> pokemonTypeRelationDTOList) {
		List<PokemonTypeRelation> pokemonTypeRelationList = pokemonTypeRelationService.addPokemonTypeRelationList(pokemonTypeRelationDTOList);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemonTypeRelationList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PokemonTypeRelation> getPokemonTypeRelationById(@PathVariable int id) {
		PokemonTypeRelation pokemonTypeRelation = pokemonTypeRelationService.getPokemonTypeRelationById(id);
		return ResponseEntity.ok(pokemonTypeRelation);
	}

	@GetMapping("/effectiveness/pair/{namePokemonType}/{nameRelatedPokemonType}")
	public ResponseEntity<Integer> getEffectivenessPercentage(@PathVariable String namePokemonType,
			@PathVariable String nameRelatedPokemonType) {
		int effectivenessPercentage = pokemonTypeRelationService.getEffectivenessPercentage(namePokemonType,
				nameRelatedPokemonType);
		return ResponseEntity.ok(effectivenessPercentage);
	}

	@GetMapping("/effectiveness/list/{namePokemonType}/{effectivenessPercentage}")
	public ResponseEntity<List<PokemonTypeRelation>> getPokemonTypeRelationByEffectivenessPercentage(
			@PathVariable String namePokemonType, @PathVariable int effectivenessPercentage) {
		List<PokemonTypeRelation> listPokemonTypeRelationByEffectivenessPercentage = pokemonTypeRelationService
				.getPokemonTypeRelationByEffectivenessPercentage(namePokemonType, effectivenessPercentage);
		return ResponseEntity.status(HttpStatus.CREATED).body(listPokemonTypeRelationByEffectivenessPercentage);
	}

	@GetMapping
	public ResponseEntity<List<PokemonTypeRelation>> getAllPokemonTypeRelations() {
		List<PokemonTypeRelation> listAllPokemonTypeRelations = pokemonTypeRelationService.getAllPokemonTypeRelations();
		return ResponseEntity.ok(listAllPokemonTypeRelations);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PokemonTypeRelation> updatePokemonTypeRelationById(@PathVariable int id,
			@RequestBody PokemonTypeRelationDTO newPokemonTypeRelationDTO) {
		PokemonTypeRelation pokemonTypeRelationById = pokemonTypeRelationService.updatePokemonTypeRelationById(id,
				newPokemonTypeRelationDTO);
		return ResponseEntity.ok(pokemonTypeRelationById);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PokemonTypeRelation> deletePokemonTypeRelationById(@PathVariable int id) {
		pokemonTypeRelationService.deletePokemonTypeRelationById(id);
		return ResponseEntity.noContent().build();
	}
}
