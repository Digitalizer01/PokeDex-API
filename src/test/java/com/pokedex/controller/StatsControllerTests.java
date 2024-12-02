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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.dto.StatsDTO;
import com.pokedex.exception.GlobalExceptionHandler;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Stats;
import com.pokedex.service.StatsService;

@ExtendWith(MockitoExtension.class)
public class StatsControllerTests {
	@Mock
	private StatsService statsService;

	@InjectMocks
	private StatsController statsController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(statsController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void testAddStatsSuccess() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, 120, 90, 110, 80, 95);
	    Stats stats = new Stats(100, 120, 90, 110, 80, 95);
	    
	    when(statsService.addStats(statsDTO)).thenReturn(stats);

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.hp").value(100))
	            .andExpect(jsonPath("$.attack").value(120));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionHp() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(-100, 120, 90, 110, 80, 95);

	    // Simulate invalid HP
	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionAttack() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, -120, 90, 110, 80, 95);

	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionDefense() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, 120, -90, 110, 80, 95);

	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionSpecialAttack() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, 120, 90, -110, 80, 95);

	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionSpecialDefense() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, 120, 90, 110, -80, 95);

	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testAddStatsExceptionSpeed() throws Exception {
		// Arrange
	    StatsDTO statsDTO = new StatsDTO(100, 120, 90, 110, 80, -95);

	    when(statsService.addStats(statsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(post("/stats")
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(statsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).addStats(statsDTO);
	}

	@Test
	public void testGetStatsByIdSuccess() throws Exception {
		// Arrange
	    int id = 1;
	    Stats stats = new Stats(100, 120, 90, 110, 80, 95);

	    when(statsService.getStatsById(id)).thenReturn(stats);

	    // Act & Assert
	    mockMvc.perform(get("/stats/{id}", id)
	            .contentType("application/json"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.hp").value(100))
	            .andExpect(jsonPath("$.attack").value(120));
	    
	    verify(statsService, times(1)).getStatsById(id);
	}

	@Test
	public void testGetStatsByIdExceptionNotFound() throws Exception {
		// Arrange
	    int id = 1;

	    when(statsService.getStatsById(id))
	        .thenThrow(new ResourceNotFoundException("Stats not found. Id: " + id));

	    // Act & Assert
	    mockMvc.perform(get("/stats/{id}", id)
	            .contentType("application/json"))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("Stats not found. Id: " + id));
	    
	    verify(statsService, times(1)).getStatsById(id);
	}

	@Test
	public void testUpdateStatsByIdSuccess() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, 130, 100, 120, 90, 105);
	    Stats updatedStats = new Stats(150, 130, 100, 120, 90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO)).thenReturn(updatedStats);

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.hp").value(150))
	            .andExpect(jsonPath("$.attack").value(130));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testUpdateStatsExceptionHp() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(-150, 130, 100, 120, 90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testUpdateStatsExceptionAttack() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, -130, 100, 120, 90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testUpdateStatsExceptionDefense() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, 130, -100, 120, 90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testUpdateStatsExceptionSpecialAttack() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, 130, 100, -120, 90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testUpdateStatsExceptionSpecialDefense() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, 130, 100, 120, -90, 105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}
	
	@Test
	public void testUpdateStatsExceptionSpecialSpeed() throws Exception {
		// Arrange
	    int id = 1;
	    StatsDTO newStatsDTO = new StatsDTO(150, 130, 100, 120, 90, -105);

	    when(statsService.updateStatsById(id, newStatsDTO))
	        .thenThrow(new ResourceInvalidDataException("Couldn't save the Stats. Invalid data"));

	    // Act & Assert
	    mockMvc.perform(put("/stats/{id}", id)
	            .contentType("application/json")
	            .content(new ObjectMapper().writeValueAsString(newStatsDTO)))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.error").value("Couldn't save the Stats. Invalid data"));
	    
	    verify(statsService, times(1)).updateStatsById(id, newStatsDTO);
	}

	@Test
	public void testDeleteStatsByIdSucess() throws Exception {
		// Arrange
	    int id = 1;

	    // Act & Assert
	    mockMvc.perform(delete("/stats/{id}", id)
	            .contentType("application/json"))
	            .andExpect(status().isNoContent());
	    
	    verify(statsService, times(1)).deleteStatsById(id);
	}

	@Test
	public void testDeleteStatsByIdExceptionNotFound() throws Exception {
		// Arrange
	    int id = 1;

	    doThrow(new ResourceNotFoundException("Stats not found. Id: " + id))
	        .when(statsService).deleteStatsById(id);

	    // Act & Assert
	    mockMvc.perform(delete("/stats/{id}", id)
	            .contentType("application/json"))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.error").value("Stats not found. Id: " + id));
	    
	    verify(statsService, times(1)).deleteStatsById(id);
	}
}
