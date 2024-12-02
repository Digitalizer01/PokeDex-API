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
import com.pokedex.dto.GenerationDTO;
import com.pokedex.exception.GlobalExceptionHandler;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Generation;
import com.pokedex.service.GenerationService;

@ExtendWith(MockitoExtension.class)
public class GenerationControllerTests {

	@Mock
	private GenerationService generationService;

	@InjectMocks
	private GenerationController generationController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(generationController)
	            .setControllerAdvice(new GlobalExceptionHandler())
	            .build(); 
	}

	// Add
	@Test
	public void testAddGenerationSuccess() throws Exception {
		// Arrange
		GenerationDTO generationDTO = new GenerationDTO(1, "Kanto", 1996);
		Generation generation = new Generation(1, "Kanto", 1996);

		when(generationService.addGeneration(generationDTO)).thenReturn(generation);

		// Act & Assert
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.number").value(1))
	            .andExpect(jsonPath("$.region").value("Kanto"))
	            .andExpect(jsonPath("$.year").value(1996));

		verify(generationService, times(1)).addGeneration(generationDTO);
	}

	@Test
	public void testAddGenerationExceptionDuplicate() throws Exception {
		// Arrange
	    GenerationDTO generationDTO = new GenerationDTO(1, "Kanto", 1996);
	    when(generationService.addGeneration(generationDTO))
	            .thenThrow(new ResourceDuplicatedException("Duplicated generation"));

	    // Act & Assert
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isConflict());
	    
	    verify(generationService, times(1)).addGeneration(generationDTO);
	}

	@Test
	public void testAddGenerationExceptionNumber() throws Exception {
		// Arrange
	    GenerationDTO generationDTO = new GenerationDTO(0, "Kanto", 1996); // Número inválido

	    when(generationService.addGeneration(generationDTO))
	            .thenThrow(new ResourceInvalidDataException("Invalid number"));

	    // Act & Assert
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isBadRequest());

	    verify(generationService, times(1)).addGeneration(generationDTO);
	}

	@Test
	public void testAddGenerationExceptionRegion() throws Exception {
		// Arrange
	    GenerationDTO generationDTOEmpty = new GenerationDTO(1, "", 1996);
	    GenerationDTO generationDTONull = new GenerationDTO(1, null, 1996);

	    when(generationService.addGeneration(generationDTOEmpty))
	    	.thenThrow(new ResourceInvalidDataException("Invalid region"));

	    when(generationService.addGeneration(generationDTONull))
        	.thenThrow(new ResourceInvalidDataException("Invalid region"));

	    // Act & Assert
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTOEmpty)))
	            .andExpect(status().isBadRequest());
	    
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTONull)))
	            .andExpect(status().isBadRequest());

	    verify(generationService, times(1)).addGeneration(generationDTOEmpty);
	    verify(generationService, times(1)).addGeneration(generationDTONull);
	}

	@Test
	public void testAddGenerationExceptionYear() throws Exception {
		// Arrange
	    GenerationDTO generationDTO = new GenerationDTO(1, "", 0);

	    when(generationService.addGeneration(generationDTO))
	    	.thenThrow(new ResourceInvalidDataException("Invalid year"));
	    
	    // Act & Assert
	    mockMvc.perform(post("/generation")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isBadRequest());
	    
	    verify(generationService, times(1)).addGeneration(generationDTO);
	}


	// Get
	@Test
	public void testGetGenerationByIdSuccess() throws Exception {
		// Arrange
	    Generation generation = new Generation(1, "Kanto", 1996);
	    when(generationService.getGenerationById(1)).thenReturn(generation);

	    // Act & Assert
	    mockMvc.perform(get("/generation/1"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.number").value(1))
	            .andExpect(jsonPath("$.region").value("Kanto"))
	            .andExpect(jsonPath("$.year").value(1996));

	    verify(generationService, times(1)).getGenerationById(1);
	}
	
	@Test
	public void testGetGenerationByIdExceptionNotFound() throws Exception {
		// Arrange
	    when(generationService.getGenerationById(1))
	            .thenThrow(new ResourceNotFoundException("Generation not found"));

	    // Act & Assert
	    mockMvc.perform(get("/generation/1"))
	            .andExpect(status().isNotFound());

	    verify(generationService, times(1)).getGenerationById(1);
	}
	
	@Test
	public void testGetAllGenerationsSuccess() throws Exception {
		// Arrange
	    List<Generation> generations = List.of(new Generation(1, "Kanto", 1996), new Generation(2, "Johto", 1999));
	    when(generationService.getAllGenerations()).thenReturn(generations);

	    // Act & Assert
	    mockMvc.perform(get("/generation"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].number").value(1))
	            .andExpect(jsonPath("$[0].region").value("Kanto"))
	            .andExpect(jsonPath("$[0].year").value(1996))
	            .andExpect(jsonPath("$[1].number").value(2))
	            .andExpect(jsonPath("$[1].region").value("Johto"))
	            .andExpect(jsonPath("$[1].year").value(1999));

	    verify(generationService, times(1)).getAllGenerations();
	}

	@Test
	public void testGetAllGenerationRegionsSuccess() throws Exception {
		// Arrange
	    List<String> regions = List.of("Kanto", "Johto");
	    when(generationService.getAllGenerationRegions()).thenReturn(regions);

	    // Act & Assert
	    mockMvc.perform(get("/generation/regions"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0]").value("Kanto"))
	            .andExpect(jsonPath("$[1]").value("Johto"));

	    verify(generationService, times(1)).getAllGenerationRegions();
	}
	
	// Update
	@Test
	public void testUpdateGenerationSuccess() throws Exception {
		// Arrange
	    GenerationDTO generationDTO = new GenerationDTO(1, "Kanto", 1996);
	    Generation updatedGeneration = new Generation(1, "Kanto", 1996);

	    when(generationService.updateGenerationById(1, generationDTO)).thenReturn(updatedGeneration);

	    // Act & Assert
	    mockMvc.perform(put("/generation/1")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.number").value(1))
	            .andExpect(jsonPath("$.region").value("Kanto"))
	            .andExpect(jsonPath("$.year").value(1996));

	    verify(generationService, times(1)).updateGenerationById(1, generationDTO);
	}

	@Test
	public void testUpdateGenerationExceptionNotFound() throws Exception {
		// Arrange
	    GenerationDTO generationDTO = new GenerationDTO(2, "Johto", 1999);
	    int id = 1;
	    when(generationService.updateGenerationById(1, generationDTO))
	            .thenThrow(new ResourceNotFoundException("Generation not found. Id: " + id));

	    // Act & Assert
	    mockMvc.perform(put("/generation/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(generationDTO)))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("Generation not found. Id: " + id));
	    
	    verify(generationService, times(1)).updateGenerationById(id, generationDTO);
	}
	
	@Test
	public void testUpdateGenerationExceptionDuplicate() throws Exception {
		// Arrange
	    GenerationDTO updatedGenerationDTO = new GenerationDTO(1, "Kanto", 1996);

	    when(generationService.updateGenerationById(1, updatedGenerationDTO))
	            .thenThrow(new ResourceDuplicatedException("Duplicated generation"));

	    // Act & Assert
	    mockMvc.perform(put("/generation/{id}", 1)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(updatedGenerationDTO)))
	            .andExpect(status().isConflict())
	            .andExpect(jsonPath("$.error").value("Duplicated generation"));

	    verify(generationService, times(1)).updateGenerationById(1, updatedGenerationDTO);
	}
	
	@Test
	public void testUpdateGenerationExceptionNumber() throws Exception {
		// Arrange
	    GenerationDTO invalidGenerationDTO = new GenerationDTO(0, "Kanto", 1996);
	    int id = 1;
	    when(generationService.updateGenerationById(id, invalidGenerationDTO))
	            .thenThrow(new ResourceInvalidDataException("Couldn't save the Generation. Invalid number: " + invalidGenerationDTO.toString()));

	    // Act & Assert
	    mockMvc.perform(put("/generation/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidGenerationDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Generation. Invalid number: " + invalidGenerationDTO.toString()));
	    
	    verify(generationService, times(1)).updateGenerationById(id, invalidGenerationDTO);
	}
	
	@Test
	public void testUpdateGenerationExceptionRegion() throws Exception {
		// Arrange
	    GenerationDTO invalidGenerationDTOEmpty = new GenerationDTO(1, "", 1996);
	    GenerationDTO invalidGenerationDTONull = new GenerationDTO(1, null, 1996);
	    int id = 1;
	    when(generationService.updateGenerationById(id, invalidGenerationDTOEmpty))
        	.thenThrow(new ResourceInvalidDataException("Couldn't save the Generation. Invalid region: " + invalidGenerationDTOEmpty.toString()));
	    when(generationService.updateGenerationById(id, invalidGenerationDTONull))
        	.thenThrow(new ResourceInvalidDataException("Couldn't save the Generation. Invalid region: " + invalidGenerationDTONull.toString()));

	    // Act & Assert
	    mockMvc.perform(put("/generation/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidGenerationDTOEmpty)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Generation. Invalid region: " + invalidGenerationDTOEmpty.toString()));
	    mockMvc.perform(put("/generation/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidGenerationDTONull)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Generation. Invalid region: " + invalidGenerationDTONull.toString()));
	    
	    verify(generationService, times(1)).updateGenerationById(id, invalidGenerationDTOEmpty);
	    verify(generationService, times(1)).updateGenerationById(id, invalidGenerationDTONull);
	}

	@Test
	public void testUpdateGenerationExceptionYear() throws Exception {
		// Arrange
	    GenerationDTO invalidGenerationDTO = new GenerationDTO(1, "Hoenn", -100);
	    int id = 1;
	    when(generationService.updateGenerationById(id, invalidGenerationDTO))
	            .thenThrow(new ResourceInvalidDataException("Couldn't save the Generation. Invalid year: " + invalidGenerationDTO.toString()));

	    // Act & Assert
	    mockMvc.perform(put("/generation/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(invalidGenerationDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Generation. Invalid year: " + invalidGenerationDTO.toString()));
	    
	    verify(generationService, times(1)).updateGenerationById(id, invalidGenerationDTO);
	}
	
	// Delete
	@Test
	public void testDeleteGenerationSuccess() throws Exception {
		// Act & Assert
	    mockMvc.perform(delete("/generation/1"))
	            .andExpect(status().isNoContent());

	    verify(generationService, times(1)).deleteGenerationById(1);
	}

	@Test
	public void testDeleteGenerationExceptionNotFound() throws Exception {
		// Arrange
	    int id = 1;
	    doThrow(new ResourceNotFoundException("Generation not found. Id: " + id))
	            .when(generationService).deleteGenerationById(id);

	    // Act & Assert
	    mockMvc.perform(delete("/generation/{id}", id))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("Generation not found. Id: " + id));

	    verify(generationService, times(1)).deleteGenerationById(id);
	}
}
