package com.pokedex.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.dto.PokemonTypeRelationDTO;
import com.pokedex.enums.EffectivenessPercentageEnum;
import com.pokedex.exception.GlobalExceptionHandler;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.PokemonType;
import com.pokedex.model.PokemonTypeRelation;
import com.pokedex.service.PokemonTypeRelationService;

@ExtendWith(MockitoExtension.class)
public class PokemonTypeRelationControllerTests {

	@Mock
	private PokemonTypeRelationService pokemonTypeRelationService;

	@InjectMocks
	private PokemonTypeRelationController pokemonTypeRelationController;

	private MockMvc mockMvc;
	
	private final PokemonType waterType = new PokemonType("Water");
	private final PokemonType fireType = new PokemonType("Fire");
	private final PokemonType dragonType = new PokemonType("Dragon");
	private final PokemonType fairyType = new PokemonType("Fairy");
	private final PokemonType rockType = new PokemonType("Rock");

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(pokemonTypeRelationController)
				.setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@Test
	public void testAddPokemonTypeRelationSuccess() throws Exception {
		// Arrange
		PokemonTypeRelationDTO pokemonTypeRelationDTO = new PokemonTypeRelationDTO("Fire", "Water", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());
	    PokemonTypeRelation pokemonTypeRelation = new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.addPokemonTypeRelation(pokemonTypeRelationDTO))
	        .thenReturn(pokemonTypeRelation);

	    // Act & Assert
	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTO)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.effectivenessPercentage").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	            .andExpect(jsonPath("$.pokemonType.name").value("Fire"))
	            .andExpect(jsonPath("$.relatedPokemonType.name").value("Water"));

	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(pokemonTypeRelationDTO);
	}

	@Test
	public void testAddPokemonTypeRelationExceptionNamePokemonType() throws Exception {
		// Arrange
		PokemonTypeRelationDTO pokemonTypeRelationDTO = new PokemonTypeRelationDTO(null, "Water", EffectivenessPercentageEnum.SUPER_EFFECTIVE.getValue());
		PokemonTypeRelationDTO pokemonTypeRelationDTONoExist = new PokemonTypeRelationDTO("NoExist", "Water", EffectivenessPercentageEnum.SUPER_EFFECTIVE.getValue());
		
		when(pokemonTypeRelationService.addPokemonTypeRelation(pokemonTypeRelationDTO))
    		.thenThrow(new ResourceInvalidDataException("Invalid data"));

		// Act & Assert
	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(pokemonTypeRelationDTO);

	    pokemonTypeRelationDTO.setNamePokemonType("");

	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(pokemonTypeRelationDTO);
	    	    
	    when(pokemonTypeRelationService.addPokemonTypeRelation(pokemonTypeRelationDTONoExist))
			.thenThrow(new ResourceNotFoundException("Type not found"));

	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTONoExist)))
	            .andExpect(status().isNotFound());
	    
	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(pokemonTypeRelationDTONoExist);
	}
	
	@Test
	public void testAddPokemonTypeRelationExceptionDuplicate() throws Exception {
		// Arrange
		PokemonTypeRelationDTO relationDTO = new PokemonTypeRelationDTO("Fire", "Water", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.addPokemonTypeRelation(relationDTO))
	            .thenThrow(new ResourceDuplicatedException("Duplicate relation"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(relationDTO)))
	            .andExpect(status().isConflict());
	    
	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(relationDTO);
	}
	
	@Test
	public void testAddPokemonTypeRelationExceptionEffectivenessPercentage() throws Exception {
		// Arrange
		PokemonTypeRelationDTO pokemonTypeRelationDTO = new PokemonTypeRelationDTO("Fire", "Water", 10);

		when(pokemonTypeRelationService.addPokemonTypeRelation(pokemonTypeRelationDTO))
			.thenThrow(new ResourceInvalidDataException("Invalid data"));
		
		// Act & Assert
	    mockMvc.perform(post("/pokemontyperelation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelation(pokemonTypeRelationDTO);
	}
	
	@Test
	public void testAddPokemonTypeRelationListSuccess() throws Exception {
		// Arrange
		PokemonTypeRelationDTO pokemonTypeRelationDTO1 = new PokemonTypeRelationDTO("Fire", "Water", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());
	    PokemonTypeRelationDTO pokemonTypeRelationDTO2 = new PokemonTypeRelationDTO("Dragon", "Fairy", EffectivenessPercentageEnum.NO_EFFECT.getValue());
	    List<PokemonTypeRelationDTO> pokemonTypeRelationDTOList = Arrays.asList(pokemonTypeRelationDTO1, pokemonTypeRelationDTO2);

	    PokemonTypeRelation relation1 = new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.SUPER_EFFECTIVE.getValue());
	    PokemonTypeRelation relation2 = new PokemonTypeRelation(dragonType, fairyType, EffectivenessPercentageEnum.NO_EFFECT.getValue());
	    List<PokemonTypeRelation> relationList = Arrays.asList(relation1, relation2);

	    when(pokemonTypeRelationService.addPokemonTypeRelationList(pokemonTypeRelationDTOList))
	        .thenReturn(relationList);
	    
	    // Act & Assert
	    mockMvc.perform(post("/pokemontyperelation/batch")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeRelationDTOList)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$[0].effectivenessPercentage").value(EffectivenessPercentageEnum.SUPER_EFFECTIVE.getValue()))
	            .andExpect(jsonPath("$[1].effectivenessPercentage").value(EffectivenessPercentageEnum.NO_EFFECT.getValue()));

	    verify(pokemonTypeRelationService, times(1)).addPokemonTypeRelationList(pokemonTypeRelationDTOList);
	}

	
	@Test
	public void testGetPokemonTypeRelationByIdSuccess() throws Exception {
		// Arrange
		PokemonTypeRelation relation = new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.getPokemonTypeRelationById(1)).thenReturn(relation);

	    // Act & Assert
	    mockMvc.perform(get("/pokemontyperelation/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.effectivenessPercentage").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	            .andExpect(jsonPath("$.pokemonType.name").value("Fire"))
	            .andExpect(jsonPath("$.relatedPokemonType.name").value("Water"));

	    verify(pokemonTypeRelationService, times(1)).getPokemonTypeRelationById(1);
	}

	
	@Test
	public void testGetPokemonTypeRelationByIdExceptionNotFound() throws Exception {
		// Arrange
		when(pokemonTypeRelationService.getPokemonTypeRelationById(1))
        	.thenThrow(new ResourceNotFoundException("PokemonTypeRelation not found. Id: 1"));

		// Act & Assert
	    mockMvc.perform(get("/pokemontyperelation/{id}", 1))
	            .andExpect(status().isNotFound());
	
	    verify(pokemonTypeRelationService, times(1)).getPokemonTypeRelationById(1);
	}

	@Test
	public void testGetEffectivenessPercentageSuccess() throws Exception {
		// Arrange
		when(pokemonTypeRelationService.getEffectivenessPercentage("Fire", "Water")).thenReturn(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

		// Act & Assert
	    mockMvc.perform(get("/pokemontyperelation/effectiveness/pair/{namePokemonType}/{nameRelatedPokemonType}", "Fire", "Water"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()));

	    verify(pokemonTypeRelationService, times(1)).getEffectivenessPercentage("Fire", "Water");
	}

	@Test
	public void testGetEffectivenessPercentageExceptionNamePokemonType() throws Exception {
	    // Arrange
	    when(pokemonTypeRelationService.getEffectivenessPercentage("NotFound1", "NotFound2"))
	        .thenThrow(new ResourceNotFoundException("Pokémon type not found"));
	    
	    when(pokemonTypeRelationService.getEffectivenessPercentage(anyString(), eq("Water")))
	        .thenThrow(new ResourceInvalidDataException("Invalid Pokémon type"));

	    // Act & Assert

	    mockMvc.perform(get("/pokemontyperelation/effectiveness/pair/NotFound1/NotFound2"))
	        .andExpect(status().isNotFound());

	    mockMvc.perform(get("/pokemontyperelation/effectiveness/pair/invalid/Water"))
	        .andExpect(status().isBadRequest());

	    verify(pokemonTypeRelationService, times(1)).getEffectivenessPercentage("NotFound1", "NotFound2");
	    verify(pokemonTypeRelationService, times(1)).getEffectivenessPercentage("invalid", "Water");
	}
	
	@Test
	public void testGetPokemonTypeRelationByEffectivenessPercentageSuccess() throws Exception {
		// Arrange
		List<PokemonTypeRelation> relationList = Arrays.asList(new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()));

	    when(pokemonTypeRelationService.getPokemonTypeRelationByEffectivenessPercentage("Fire", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	        .thenReturn(relationList);

	    // Act & Assert
	    mockMvc.perform(get("/pokemontyperelation/effectiveness/list/{namePokemonType}/{effectivenessPercentage}", "Fire", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$[0].relatedPokemonType.name").value("Water"))
        		.andExpect(jsonPath("$[0].effectivenessPercentage").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()));

	    verify(pokemonTypeRelationService, times(1)).getPokemonTypeRelationByEffectivenessPercentage("Fire", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());
	}

	@Test
	public void testGetPokemonTypeRelationByEffectivenessPercentageExceptionNamePokemonType() throws Exception {
	    // Arrange
	    when(pokemonTypeRelationService.getPokemonTypeRelationByEffectivenessPercentage(eq("InvalidType"), anyInt()))
	        .thenThrow(new ResourceNotFoundException("Invalid Pokémon type"));

	    // Act & Assert

	    mockMvc.perform(get("/pokemontyperelation/effectiveness/list/InvalidType/0"))
	        .andExpect(status().isNotFound());
	    
	    verify(pokemonTypeRelationService, times(1))
	        .getPokemonTypeRelationByEffectivenessPercentage(eq("InvalidType"), anyInt());
	}


	@Test
	public void testGetPokemonTypeRelationByEffectivenessPercentageExceptionEffectivenessPercentage() throws Exception {
	    // Arrange
	    when(pokemonTypeRelationService.getPokemonTypeRelationByEffectivenessPercentage(anyString(), eq(10)))
	        .thenThrow(new ResourceInvalidDataException("Invalid effectiveness percentage"));

	    // Act & Assert
	    mockMvc.perform(get("/pokemontyperelation/effectiveness/list/Fire/10"))
	        .andExpect(status().isBadRequest());

	    verify(pokemonTypeRelationService, times(1))
	        .getPokemonTypeRelationByEffectivenessPercentage(anyString(), eq(10));
	}

	
	@Test
	public void testGetAllPokemonTypeRelationsSuccess() throws Exception {
		// Arrange
		List<PokemonTypeRelation> relations = Arrays.asList(
	            new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()),
	            new PokemonTypeRelation(waterType, rockType, EffectivenessPercentageEnum.NORMAL.getValue()));

	    when(pokemonTypeRelationService.getAllPokemonTypeRelations()).thenReturn(relations);

	    // Act & Assert
	    mockMvc.perform(get("/pokemontyperelation"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].pokemonType.name").value("Fire"))
	            .andExpect(jsonPath("$[0].relatedPokemonType.name").value("Water"))
	            .andExpect(jsonPath("$[0].effectivenessPercentage").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	            .andExpect(jsonPath("$[1].pokemonType.name").value("Water"))
	            .andExpect(jsonPath("$[1].relatedPokemonType.name").value("Rock"))
	            .andExpect(jsonPath("$[1].effectivenessPercentage").value(EffectivenessPercentageEnum.NORMAL.getValue()));

	    verify(pokemonTypeRelationService, times(1)).getAllPokemonTypeRelations();
	}
	
	@Test
	public void testUpdatePokemonTypeRelationByIdSuccess() throws Exception {
		// Arrange
		PokemonTypeRelationDTO updateDTO = new PokemonTypeRelationDTO("Fire", "Water", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());
	    PokemonTypeRelation updatedRelation = new PokemonTypeRelation(fireType, waterType, EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class)))
	        .thenReturn(updatedRelation);

	    // Act & Assert
	    mockMvc.perform(put("/pokemontyperelation/{id}", 1)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updateDTO)))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$.effectivenessPercentage").value(EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue()))
	            .andExpect(jsonPath("$.pokemonType.name").value("Fire"))
	            .andExpect(jsonPath("$.relatedPokemonType.name").value("Water"));

	    verify(pokemonTypeRelationService, times(1)).updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class));
	}
	
	@Test
	public void testUpdatePokemonTypeRelationByIdExceptionNotFound() throws Exception {
		// Arrange
		PokemonTypeRelationDTO relationDTO = new PokemonTypeRelationDTO("Fire", "Water", EffectivenessPercentageEnum.NOT_VERY_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class)))
	            .thenThrow(new ResourceNotFoundException("Relation not found"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontyperelation/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(relationDTO)))
	            .andExpect(status().isNotFound());
	    
	    verify(pokemonTypeRelationService, times(1)).updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class));
	}

	@Test
	public void testUpdatePokemonTypeRelationByIdExceptionNamePokemonType() throws Exception {
		// Arrange
		PokemonTypeRelationDTO relationDTO = new PokemonTypeRelationDTO("InvalidType", "Water", EffectivenessPercentageEnum.SUPER_EFFECTIVE.getValue());

	    when(pokemonTypeRelationService.updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class)))
	            .thenThrow(new ResourceInvalidDataException("Invalid Pokémon type"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontyperelation/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(relationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(pokemonTypeRelationService, times(1)).updatePokemonTypeRelationById(eq(1), any(PokemonTypeRelationDTO.class));
	}
	
	@Test
	public void testUpdatePokemonTypeRelationByIdExceptionEffectivenessPercentage() throws Exception {
		// Arrange
		PokemonTypeRelationDTO relationDTO = new PokemonTypeRelationDTO("Fire", "Water", 10);

	    when(pokemonTypeRelationService.updatePokemonTypeRelationById(eq(1), eq(relationDTO)))
	            .thenThrow(new ResourceInvalidDataException("Invalid effectiveness percentage"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontyperelation/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(relationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(pokemonTypeRelationService, times(1)).updatePokemonTypeRelationById(eq(1), eq(relationDTO));
	}
	
	@Test
	public void testDeletePokemonTypeRelationByIdSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(delete("/pokemontyperelation/{id}", 1))
        .andExpect(status().isNoContent());

		verify(pokemonTypeRelationService, times(1)).deletePokemonTypeRelationById(1);
	}
	
	@Test
	public void testDeletePokemonTypeRelationByIdExceptionNotFound() throws Exception {
	    // Arrange
	    doThrow(new ResourceNotFoundException("Relation not found"))
	            .when(pokemonTypeRelationService).deletePokemonTypeRelationById(eq(1));

	    // Act & Assert
	    mockMvc.perform(delete("/pokemontyperelation/1"))
	            .andExpect(status().isNotFound());
	    
	    verify(pokemonTypeRelationService, times(1)).deletePokemonTypeRelationById(eq(1));
	}
}