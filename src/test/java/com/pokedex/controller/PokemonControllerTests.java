package com.pokedex.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.dto.PokemonDTO;
import com.pokedex.dto.StatsDTO;
import com.pokedex.exception.GlobalExceptionHandler;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Generation;
import com.pokedex.model.Pokemon;
import com.pokedex.model.PokemonType;
import com.pokedex.model.Stats;
import com.pokedex.service.PokemonService;

@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private PokemonService pokemonService;

	@InjectMocks
	private PokemonController pokemonController;
	
	PokemonType electricType = new PokemonType("Electric");
	PokemonType fireType = new PokemonType("Fire");
	PokemonType grassType = new PokemonType("Grass");
	PokemonType poisonType = new PokemonType("Posion");
	
	Generation redBlueGeneration = new Generation(1, "Kanto", 1996);
	Generation goldSilverGeneration = new Generation(2, "Johto", 1999);
	
	Stats stats = new Stats(45, 49, 49, 65, 65, 45);
	StatsDTO statsDTO = new StatsDTO(45, 49, 49, 65, 65, 45);
		
	Pokemon pikachu = new Pokemon(25, "Pikachu", electricType, null, "Mouse Pokemon", stats, redBlueGeneration, false);
	Pokemon charmander = new Pokemon(4, "Charmander", fireType, null, "Lizard Pokemon", stats, redBlueGeneration, false);
	Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", grassType, poisonType, "Seed Pokemon", stats, redBlueGeneration, false);
	
	PokemonDTO pikachuDTO = new PokemonDTO(25, "Pikachu", "Electric", null, "Mouse Pokemon", statsDTO, 1, false);
	PokemonDTO charmanderDTO = new PokemonDTO(4, "Charmander", "Fire", null, "Lizard Pokemon", statsDTO, 1, false);
	PokemonDTO bulbasaurDTO = new PokemonDTO(1, "Bulbasaur", "Grass", "Posion", "Seed Pokemon", statsDTO, 1, false);

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(pokemonController)
				.setControllerAdvice(new GlobalExceptionHandler())
	            .build();
	}
	
	@Test
	public void testAddPokemonSuccess() throws Exception {
		// Arrange
	    when(pokemonService.addPokemon(pikachuDTO)).thenReturn(pikachu);
	    
	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.name", is("Pikachu")))
	            .andExpect(jsonPath("$.pokemonType1.name", is("Electric")));
	    
		verify(pokemonService, times(1)).addPokemon(pikachuDTO);
	}
	
	@Test
	public void testAddPokemonExceptionDuplicate() throws Exception {
		// Arrange
	    when(pokemonService.addPokemon(any(PokemonDTO.class)))
	            .thenThrow(new ResourceDuplicatedException("Pokemon already exists"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isConflict());
	    
		verify(pokemonService, times(1)).addPokemon(any(PokemonDTO.class));
	}
	
	@Test
	public void testAddPokemonExceptionName() throws Exception {
		// Arrange
	    PokemonDTO invalidNameDTO = new PokemonDTO(25, "", "Electric", null, "Mouse Pokemon", statsDTO, 1, false);
	    when(pokemonService.addPokemon(any(PokemonDTO.class)))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokemon name"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidNameDTO)))
	            .andExpect(status().isBadRequest());
	    
		verify(pokemonService, times(1)).addPokemon(any(PokemonDTO.class));
	}
	
	@Test
	public void testAddPokemonExceptionNameType() throws Exception {
		// Arrange
	    PokemonDTO invalidTypeDTO = new PokemonDTO(25, "Pikachu", "InvalidType", null, "Mouse Pokemon", statsDTO, 1, false);
	    when(pokemonService.addPokemon(any(PokemonDTO.class)))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokemon type"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidTypeDTO)))
	            .andExpect(status().isBadRequest());
	    
		verify(pokemonService, times(1)).addPokemon(any(PokemonDTO.class));
	}
	
	@Test
	public void testAddPokemonExceptionStats() throws Exception {
		// Arrange
	    StatsDTO invalidStatsDTO = new StatsDTO(-10, 49, 49, 65, 65, 45);
	    PokemonDTO invalidStatsPokemonDTO = new PokemonDTO(25, "Pikachu", "Electric", null, "Mouse Pokemon", invalidStatsDTO, 1, false);
	    when(pokemonService.addPokemon(any(PokemonDTO.class)))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokemon stats"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidStatsPokemonDTO)))
	            .andExpect(status().isBadRequest());
	    
		verify(pokemonService, times(1)).addPokemon(any(PokemonDTO.class));
	}
	
	@Test
	public void testAddPokemonExceptionIdGeneration() throws Exception {
		// Arrange
	    PokemonDTO invalidGenerationDTO = new PokemonDTO(25, "Pikachu", "Electric", null, "Mouse Pokemon", statsDTO, 99, false); // 99 is an invalidad generation
	    when(pokemonService.addPokemon(any(PokemonDTO.class)))
	            .thenThrow(new ResourceInvalidDataException("Invalid generation ID"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemon")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidGenerationDTO)))
	            .andExpect(status().isBadRequest());
	    
		verify(pokemonService, times(1)).addPokemon(any(PokemonDTO.class));
	}
	
	@Test
	public void testAddPokemonListSuccess() throws Exception {
		// Arrange
	    List<PokemonDTO> pokemonDTOList = Arrays.asList(pikachuDTO, charmanderDTO, bulbasaurDTO);
	    List<Pokemon> pokemonList = Arrays.asList(pikachu, charmander, bulbasaur);
	    when(pokemonService.addPokemonList(anyList())).thenReturn(pokemonList);

	    // Act & Assert
	    mockMvc.perform(post("/pokemon/batch")
	    		.contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonDTOList)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$[0].name", is("Pikachu")))
	            .andExpect(jsonPath("$[1].name", is("Charmander")))
	            .andExpect(jsonPath("$[2].name", is("Bulbasaur")));
	    
		verify(pokemonService, times(1)).addPokemonList(anyList());
	}
	
	@Test
	public void testGetAllPokemon() throws Exception {
		// Arrange
	    List<Pokemon> pokemonList = Arrays.asList(pikachu, charmander, bulbasaur);
	    when(pokemonService.getAllPokemon()).thenReturn(pokemonList);

	    // Act
	    mockMvc.perform(get("/pokemon"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].name", is("Pikachu")))
	            .andExpect(jsonPath("$[1].name", is("Charmander")))
	            .andExpect(jsonPath("$[2].name", is("Bulbasaur")));

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemon();
	}
	
	@Test
	public void testGetAllPokemonByType1NameSuccess() throws Exception {
		// Arrange
	    List<Pokemon> electricPokemons = Collections.singletonList(pikachu);
	    when(pokemonService.getAllPokemonByPokemonType1Name("Electric")).thenReturn(electricPokemons);

	    // Act
	    mockMvc.perform(get("/pokemon/type1/name/Electric"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].name", is("Pikachu")));

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType1Name("Electric");
	}
	
	@Test
	public void testGetAllPokemonByType1NameExceptionName() throws Exception {
		// Arrange
	    String invalidTypeName = "InvalidType";

	    when(pokemonService.getAllPokemonByPokemonType1Name(invalidTypeName))
	            .thenThrow(new ResourceNotFoundException("Pokemon type not found"));

	    // Act & Assert
	    mockMvc.perform(get("/pokemon/type1/name/" + invalidTypeName))
	            .andExpect(status().isNotFound());

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType1Name(invalidTypeName);
	}
	
	@Test
	public void testGetAllPokemonByType2NameSuccess() throws Exception {
		// Arrange
	    List<Pokemon> firePokemons = Collections.singletonList(charmander);
	    when(pokemonService.getAllPokemonByPokemonType2Name("Fire")).thenReturn(firePokemons);

	    // Act
	    mockMvc.perform(get("/pokemon/type2/name/Fire"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].name", is("Charmander")));

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType2Name("Fire");
	}
	
	@Test
	public void testGetAllPokemonByType2NameExceptionName() throws Exception {
		// Arrange
	    String invalidTypeName = "InvalidType";
	    when(pokemonService.getAllPokemonByPokemonType2Name(invalidTypeName))
	            .thenThrow(new ResourceNotFoundException("Pokemon type not found"));

	    // Act
	    mockMvc.perform(get("/pokemon/type2/name/" + invalidTypeName))
	            .andExpect(status().isNotFound());

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType2Name(invalidTypeName);
	}
	
	@Test
	public void testGetAllPokemonByPokemonType1NameAndPokemonType2NameSuccess() throws Exception {
		// Arrange
	    List<Pokemon> grassPoisonPokemons = Collections.singletonList(bulbasaur);
	    List<Pokemon> firePokemons = Collections.singletonList(charmander);
	    
	    when(pokemonService.getAllPokemonByPokemonType1NameAndPokemonType2Name("Grass", "Posion"))
        	.thenReturn(grassPoisonPokemons);
	    
	    when(pokemonService.getAllPokemonByPokemonType1NameAndPokemonType2Name("Fire", null))
        	.thenReturn(firePokemons);

	    // Act
	    mockMvc.perform(get("/pokemon/type1andtype2?namePokemonType1=Grass&namePokemonType2=Posion"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].name", is("Bulbasaur")));
	    
	    mockMvc.perform(get("/pokemon/type1andtype2?namePokemonType1=Fire"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].name", is("Charmander")));

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType1NameAndPokemonType2Name("Grass", "Posion");
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType1NameAndPokemonType2Name("Fire", null);
	}
	
	@Test
	public void testGetAllPokemonByPokemonType1NameAndPokemonType2NameExceptionName() throws Exception {
	    // Arrange
		when(pokemonService.getAllPokemonByPokemonType1NameAndPokemonType2Name("Poison", "Poison"))
        .thenThrow(new ResourceInvalidDataException("Pokemon type 1 and type 2 cannot be equals"));

	    // Act
	    mockMvc.perform(get("/pokemon/type1andtype2?namePokemonType1=Poison&namePokemonType2=Poison"))
	    .andExpect(status().isBadRequest());

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByPokemonType1NameAndPokemonType2Name("Poison", "Poison");
	}
	
	@Test
	public void testGetAllPokemonByGenerationRegionSuccess() throws Exception {
		// Arrange
	    List<Pokemon> kantoPokemons = Arrays.asList(pikachu, charmander);
	    when(pokemonService.getAllPokemonByGenerationRegion("Kanto")).thenReturn(kantoPokemons);

	    // Act
	    mockMvc.perform(get("/pokemon/generation/region/Kanto"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].name", is("Pikachu")))
	            .andExpect(jsonPath("$[1].name", is("Charmander")));

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByGenerationRegion("Kanto");
	}
	
	@Test
	public void testGetAllPokemonByGenerationRegionExceptionNotFound() throws Exception {
		// Arrange
	    when(pokemonService.getAllPokemonByGenerationRegion("InvalidRegion"))
	            .thenThrow(new ResourceNotFoundException("Region not found"));

	    // Act
	    mockMvc.perform(get("/pokemon/generation/region/InvalidRegion"))
	            .andExpect(status().isNotFound());

	    // Verify
	    verify(pokemonService, times(1)).getAllPokemonByGenerationRegion("InvalidRegion");
	}
	
	@Test
	public void testUpdatePokemonByIdSuccess() throws Exception {
		// Arrange
	    when(pokemonService.updatePokemonById(25, pikachuDTO)).thenReturn(pikachu);

	    // Act
	    mockMvc.perform(put("/pokemon/25")
    	       .contentType("application/json")
    	       .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
    	       .andExpect(status().isOk())
    	       .andExpect(jsonPath("$.name", is("Pikachu")));

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	@Test
	public void testUpdatePokemonByIdExceptioNotFound() throws Exception {
		// Arrange
	    when(pokemonService.updatePokemonById(999, pikachuDTO))
	            .thenThrow(new ResourceNotFoundException("Pokemon not found"));

	    // Act
	    mockMvc.perform(put("/pokemon/999")
	    	    .contentType("application/json")
        		.content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	    	    .andExpect(status().isNotFound());

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(999, pikachuDTO);
	}
	
	@Test
	public void testUpdatePokemonByIdExceptionDuplicate() throws Exception {
		// Arrange
	    when(pokemonService.updatePokemonById(25, pikachuDTO))
	            .thenThrow(new ResourceInvalidDataException("Duplicate Pokémon"));

	    // Act
	    mockMvc.perform(put("/pokemon/25")
	            .contentType("application/json")
        		.content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isBadRequest());

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	@Test
	public void testUpdatePokemonByIdExceptionName() throws Exception {
		// Arrange
	    pikachuDTO.setName("");  // Invalid name
	    when(pokemonService.updatePokemonById(25, pikachuDTO))
	            .thenThrow(new ResourceInvalidDataException("Invalid name"));

	    // Act
	    mockMvc.perform(put("/pokemon/25")
	            .contentType("application/json")
        		.content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isBadRequest());

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	@Test
	public void testUpdatePokemonByIdExceptionNameType() throws Exception {
		// Arrange
	    pikachuDTO.setNamePokemonType1("InvalidType");  // Invalid type
	    when(pokemonService.updatePokemonById(25, pikachuDTO))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokémon type"));

	    // Act
	    mockMvc.perform(put("/pokemon/25")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isBadRequest());

	    // 
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	@Test
	public void testUpdatePokemonByIdExceptionStats() throws Exception {
		// Arrange
	    pikachuDTO.getStats().setAttack(-1);  // Invalid stats
	    when(pokemonService.updatePokemonById(25, pikachuDTO))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokémon stats"));

	    // Act
	    mockMvc.perform(put("/pokemon/25")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isBadRequest());

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	
	@Test
	public void testUpdatePokemonByIdExceptionIdGeneration() throws Exception {
		// Arrange
	    pikachuDTO.setIdGeneration(999);  // Invalid generation
	    when(pokemonService.updatePokemonById(25, pikachuDTO))
	            .thenThrow(new ResourceInvalidDataException("Invalid generation ID"));

	    // Act
	    mockMvc.perform(put("/pokemon/25")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pikachuDTO)))
	            .andExpect(status().isBadRequest());

	    // Verify
	    verify(pokemonService, times(1)).updatePokemonById(25, pikachuDTO);
	}
	
	@Test
	public void testDeletePokemonByIdSuccess() throws Exception {
	    // Act
	    mockMvc.perform(delete("/pokemon/25"))
	            .andExpect(status().isNoContent());

	    // Verify
	    verify(pokemonService, times(1)).deletePokemonById(25);
	}
	
	@Test
	public void testDeletePokemonByIdExceptionNotFound() throws Exception {
		// Arrange
		int id = 999;
	    doThrow(new ResourceNotFoundException("Pokemon not found. Id: " + id))
	            .when(pokemonService).deletePokemonById(id);

	    // Act
	    mockMvc.perform(delete("/pokemon/999"))
	            .andExpect(status().isNotFound());

	    // Verify
	    verify(pokemonService, times(1)).deletePokemonById(999);
	}
}
