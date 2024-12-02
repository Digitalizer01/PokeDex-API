package com.pokedex.controller;

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
import com.pokedex.dto.PokemonTypeDTO;
import com.pokedex.exception.GlobalExceptionHandler;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.PokemonType;
import com.pokedex.service.PokemonTypeService;

@ExtendWith(MockitoExtension.class)
public class PokemonTypeControllerTests {

	@Mock
	private PokemonTypeService pokemonTypeService;

	@InjectMocks
	private PokemonTypeController pokemonTypeController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(pokemonTypeController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void testPokemonTypeAddSuccess() throws Exception {
		// Arrange
	    PokemonTypeDTO pokemonTypeDTO = new PokemonTypeDTO("Fire");
	    PokemonType pokemonType = new PokemonType("Fire");

	    when(pokemonTypeService.addPokemonType(pokemonTypeDTO)).thenReturn(pokemonType);

	    // Act & Assert
	    mockMvc.perform(post("/pokemontype")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeDTO)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.name").value("Fire"));

	    verify(pokemonTypeService, times(1)).addPokemonType(pokemonTypeDTO);
	}
	
	@Test
	public void testPokemonTypeExceptionDuplicate() throws Exception {
		// Arrange
	    PokemonTypeDTO pokemonTypeDTO = new PokemonTypeDTO("Fire");

	    when(pokemonTypeService.addPokemonType(pokemonTypeDTO))
	            .thenThrow(new ResourceDuplicatedException("Duplicated Pokemon type"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemontype")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeDTO)))
	            .andExpect(status().isConflict())
	            .andExpect(jsonPath("$.error").value("Duplicated Pokemon type"));

	    verify(pokemonTypeService, times(1)).addPokemonType(pokemonTypeDTO);
	}
	
	@Test
	public void testPokemonTypeExceptionName() throws Exception {
		// Arrange
	    PokemonTypeDTO pokemonTypeDTOEmpty = new PokemonTypeDTO("");
	    PokemonTypeDTO pokemonTypeDTONull = new PokemonTypeDTO(null);

	    when(pokemonTypeService.addPokemonType(pokemonTypeDTOEmpty))
	    	.thenThrow(new ResourceInvalidDataException("Invalid data: name is empty"));
	    when(pokemonTypeService.addPokemonType(pokemonTypeDTONull))
        	.thenThrow(new ResourceInvalidDataException("Invalid data: name is null"));

	    // Act & Assert
	    mockMvc.perform(post("/pokemontype")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeDTOEmpty)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Invalid data: name is empty"));
	    mockMvc.perform(post("/pokemontype")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(pokemonTypeDTONull)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Invalid data: name is null"));

	    verify(pokemonTypeService, times(1)).addPokemonType(pokemonTypeDTOEmpty);
	    verify(pokemonTypeService, times(1)).addPokemonType(pokemonTypeDTONull);
	}
	
	@Test
	public void testPokemonTypeGetPokemonByIdSuccess() throws Exception {
		 // Arrange
	    PokemonType pokemonType = new PokemonType("Fire");

	    when(pokemonTypeService.getPokemonTypeById(1)).thenReturn(pokemonType);

	    // Act & Assert
	    mockMvc.perform(get("/pokemontype/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name").value("Fire"));

	    verify(pokemonTypeService, times(1)).getPokemonTypeById(1);	
	}
	
	@Test
	public void testPokemonTypeGetPokemonByIdExceptionNotFound() throws Exception {
		// Arrange
	    int id = 1;

	    when(pokemonTypeService.getPokemonTypeById(id))
	            .thenThrow(new ResourceNotFoundException("PokemonType not found. Id: " + id));

	    // Act & Assert
	    mockMvc.perform(get("/pokemontype/{id}", id))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("PokemonType not found. Id: " + id));

	    verify(pokemonTypeService, times(1)).getPokemonTypeById(id);
	}
	
	@Test
	public void testPokemonTypeGetAllPokemonTypesSucess() throws Exception {
		// Arrange
	    List<PokemonType> pokemonTypes = List.of(new PokemonType("Fire"), new PokemonType("Water"));

	    when(pokemonTypeService.getAllPokemonTypes()).thenReturn(pokemonTypes);

	    // Act & Assert
	    mockMvc.perform(get("/pokemontype"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.length()").value(2))
	            .andExpect(jsonPath("$[0].name").value("Fire"))
	            .andExpect(jsonPath("$[1].name").value("Water"));

	    verify(pokemonTypeService, times(1)).getAllPokemonTypes();
	}

	@Test
	public void testPokemonTypeUpdatePokemonTypeByIdSuccess() throws Exception {
		// Arrange
	    PokemonTypeDTO updatedPokemonTypeDTO = new PokemonTypeDTO("Electric");
	    PokemonType updatedPokemonType = new PokemonType("Electric");

	    when(pokemonTypeService.updatePokemonTypeById(1, updatedPokemonTypeDTO)).thenReturn(updatedPokemonType);

	    // Act & Assert
	    mockMvc.perform(put("/pokemontype/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedPokemonTypeDTO)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name").value("Electric"));

	    verify(pokemonTypeService, times(1)).updatePokemonTypeById(1, updatedPokemonTypeDTO);
	}
	
	@Test
	public void testPokemonTypeUpdatePokemonTypeByIdExceptionNotFound() throws Exception {
		// Arrange
	    PokemonTypeDTO updatedPokemonTypeDTO = new PokemonTypeDTO("Electric");

	    when(pokemonTypeService.updatePokemonTypeById(1, updatedPokemonTypeDTO))
	            .thenThrow(new ResourceNotFoundException("PokemonType not found. Id: 1"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontype/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedPokemonTypeDTO)))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("PokemonType not found. Id: 1"));

	    verify(pokemonTypeService, times(1)).updatePokemonTypeById(1, updatedPokemonTypeDTO);
	}
	
	@Test
	public void testPokemonTypeUpdatePokemonTypeByIdExceptionDuplicate() throws Exception {
		// Arrange
	    PokemonTypeDTO updatedPokemonTypeDTO = new PokemonTypeDTO("Electric");

	    when(pokemonTypeService.updatePokemonTypeById(1, updatedPokemonTypeDTO))
	            .thenThrow(new ResourceDuplicatedException("Duplicated Pokemon type"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontype/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedPokemonTypeDTO)))
	            .andExpect(status().isConflict())
	            .andExpect(jsonPath("$.error").value("Duplicated Pokemon type"));

	    verify(pokemonTypeService, times(1)).updatePokemonTypeById(1, updatedPokemonTypeDTO);
	}
	
	@Test
	public void testPokemonTypeUpdatePokemonTypeByIdExceptionName() throws Exception {
		// Arrange
	    PokemonTypeDTO updatedPokemonTypeDTOEmpty = new PokemonTypeDTO("");
	    PokemonTypeDTO updatedPokemonTypeDTONull = new PokemonTypeDTO(null);

	    when(pokemonTypeService.updatePokemonTypeById(1, updatedPokemonTypeDTOEmpty))
        	.thenThrow(new ResourceInvalidDataException("Invalid data: name is empty"));
	    when(pokemonTypeService.updatePokemonTypeById(1, updatedPokemonTypeDTONull))
        	.thenThrow(new ResourceInvalidDataException("Invalid data: name is null"));

	    // Act & Assert
	    mockMvc.perform(put("/pokemontype/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedPokemonTypeDTOEmpty)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Invalid data: name is empty"));
	    mockMvc.perform(put("/pokemontype/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedPokemonTypeDTONull)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Invalid data: name is null"));

	    verify(pokemonTypeService, times(1)).updatePokemonTypeById(1, updatedPokemonTypeDTOEmpty);
	    verify(pokemonTypeService, times(1)).updatePokemonTypeById(1, updatedPokemonTypeDTONull);
	}
	
	// TODO
	@Test
	public void testPokemonTypeDeleteByIdSuccess() throws Exception {
		// Arrange
	    int idToDelete = 1;

	    // Act & Assert
	    mockMvc.perform(delete("/pokemontype/{id}", idToDelete))
	            .andExpect(status().isNoContent());

	    verify(pokemonTypeService, times(1)).deletePokemonTypeById(idToDelete);
	}
	
	@Test
	public void testPokemonTypeDeleteByIdExceptionNotFound() throws Exception {
		// Arrange
	    int id = 1;

	    doThrow(new ResourceNotFoundException("PokemonType not found. Id: " + id))
	            .when(pokemonTypeService).deletePokemonTypeById(id);

	    // Act & Assert
	    mockMvc.perform(delete("/pokemontype/{id}", id))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("PokemonType not found. Id: " + id));

	    verify(pokemonTypeService, times(1)).deletePokemonTypeById(id);
	}
}
