package com.bloodBank.User_Service.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.bloodBank.User_Service.Controller.HospitoController;
import com.bloodBank.User_Service.Exceptions.DataAlreadyPresent;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Service.HospitolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class HospitolControllerTest {
	private MockMvc mockMvc;
	@Mock
	 HospitolService hospitolservice;
	 @InjectMocks
	    private HospitoController hospitolcontroller;
	    private ObjectMapper objectMapper = new ObjectMapper(); 
	    
	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(hospitolcontroller).build();
	        objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	    }
	    
	    @Test
	    public void testAddHospitol_Success_AdminRole() throws Exception {
	        Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);
	        when(hospitolservice.addHospitol(any(Hospitol.class))).thenReturn(hospitol);

	        
	        mockMvc.perform(post("/user/hospitol/addHospitol")
	                .header("X-User-Role", "ADMIN")  // Admin role
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.hospitolName").value("Apollo Hospital"))
	                .andExpect(jsonPath("$.hospitolAddress").value("Chennai"))
	                .andExpect(jsonPath("$.phoneno").value("1234567890"))
	                .andExpect(jsonPath("$.emailId").value("apollo@gmail.com"));

	        // Verify interactions
	        verify(hospitolservice, times(1)).addHospitol(any(Hospitol.class));
	    }
	    
	    @Test
	    public void testAddHospitol_UnauthorizedAccess_NonAdminRole() throws Exception {
	    	 Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);

	        //  Non-admin role should get FORBIDDEN (403)
	        mockMvc.perform(post("/user/hospitol/addHospitol")
	                .header("X-User-Role", "USER")  // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isForbidden());

	        // Verify that the service method was never called
	        verify(hospitolservice, never()).addHospitol(any(Hospitol.class));
	    }

	    @Test
	    public void testAddHospitol_ThrowsException() throws Exception {
	        // Adding a duplicate hospital
	        Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L);

	        // Mock the service to throw DataAlreadyPresent exception
	        when(hospitolservice.addHospitol(any(Hospitol.class)))
	                .thenThrow(new DataAlreadyPresent("The donor:Apollo Hospitalis already registered in db"));

	        // Expect 409 Conflict
	        mockMvc.perform(post("/user/hospitol/addHospitol")
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isConflict())  // Expect HTTP 409 Conflict
	                .andExpect(content().string("The donor:Apollo Hospitalis already registered in db")); // Ensure response message matches

	        // Verify interactions
	        verify(hospitolservice, times(1)).addHospitol(any(Hospitol.class));
	    }
	    @Test
	    public void testGetHospitoldetails_AdminRole_ReturnsHospitols() throws Exception {
	        // Mock hospital data
	        List<Hospitol> hospitols = Arrays.asList(
	            new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L),
	            new Hospitol(2L, "Fortis Hospital", "Bangalore", "fortis@gmail.com", 9876543210L)
	        );

	        when(hospitolservice.getHospitolDetails()).thenReturn(hospitols);

	        // Perform the request
	        mockMvc.perform(get("/user/hospitol/getHospitol")
	                .header("X-User-Role", "ADMIN")  // Valid role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())  // Expect 200 OK
	                .andExpect(jsonPath("$.length()").value(2))  // Expect 2 hospitals in response
	                .andExpect(jsonPath("$[0].hospitolName").value("Apollo Hospital"))
	                .andExpect(jsonPath("$[1].hospitolName").value("Fortis Hospital"));

	        // Verify the service was called once
	        verify(hospitolservice, times(1)).getHospitolDetails();
	    }
	    
	    @Test
	    public void testGetHospitoldetails_UserRole_ReturnsHospitols() throws Exception {
	        List<Hospitol> hospitols = Arrays.asList(
	            new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L)
	        );

	        when(hospitolservice.getHospitolDetails()).thenReturn(hospitols);

	        mockMvc.perform(get("/user/hospitol/getHospitol")
	                .header("X-User-Role", "USER")  // Valid role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())  // Expect 200 OK
	                .andExpect(jsonPath("$.length()").value(1))  // Expect 1 hospital
	                .andExpect(jsonPath("$[0].hospitolName").value("Apollo Hospital"));

	        verify(hospitolservice, times(1)).getHospitolDetails();
	    }

	    @Test
	    public void testGetHospitoldetails_ForbiddenRole_Returns403() throws Exception {
	        mockMvc.perform(get("/user/hospitol/getHospitol")
	                .header("X-User-Role", "GUEST")  // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden());  // Expect 403 Forbidden

	        verify(hospitolservice, never()).getHospitolDetails();
	    }
	    
	    @Test
	    public void testGetHospitolById_AdminRole_ReturnsHospital() throws Exception {
	        // Mock hospital data
	        Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L);

	        when(hospitolservice.getById(1L)).thenReturn(hospitol);

	        // Perform GET request
	        mockMvc.perform(get("/user/hospitol/getHospitolbyId/1")
	                .header("X-User-Role", "ADMIN") // Valid role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // Expect 200 OK
	                .andExpect(jsonPath("$.hospitolName").value("Apollo Hospital"));

	        // Verify service call
	        verify(hospitolservice, times(1)).getById(1L);
	    }

	    @Test
	    public void testGetHospitolById_UserRole_ReturnsHospital() throws Exception {
	        Hospitol hospitol = new Hospitol(2L, "Fortis Hospital", "Bangalore", "fortis@gmail.com", 9876543210L);

	        when(hospitolservice.getById(2L)).thenReturn(hospitol);

	        mockMvc.perform(get("/user/hospitol/getHospitolbyId/2")
	                .header("X-User-Role", "USER") // Valid role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // Expect 200 OK
	                .andExpect(jsonPath("$.hospitolName").value("Fortis Hospital"));

	        verify(hospitolservice, times(1)).getById(2L);
	    }
	    @Test
	    public void testGetHospitolById_ForbiddenRole_Returns403() throws Exception {
	        mockMvc.perform(get("/user/hospitol/getHospitolbyId/1")
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // Expect 403 Forbidden

	        verify(hospitolservice, never()).getById(99L);
	    }
	    @Test
	    public void testUpdateHospitol_AdminRole_Success() throws Exception {
	        // Mock hospital data
	        Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L);

	        when(hospitolservice.updateHospitol(any(Hospitol.class))).thenReturn(hospitol);

	        // Perform POST request
	        mockMvc.perform(post("/user/hospitol/update")
	                .header("X-User-Role", "ADMIN") // Valid role
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isOk()) // Expect 200 OK
	                .andExpect(jsonPath("$.hospitolName").value("Apollo Hospital"));   
	        // Verify service call
	        verify(hospitolservice, times(1)).updateHospitol(any(Hospitol.class));
	    }
	    @Test
	    public void testUpdateHospitol_ForbiddenRole_Returns403() throws Exception {
	        Hospitol hospitol = new Hospitol(1L, "Apollo Hospital", "Chennai", "apollo@gmail.com", 1234567890L);

	        mockMvc.perform(post("/user/hospitol/update")
	                .header("X-User-Role", "USER") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isForbidden()); // Expect 403 Forbidden

	        verify(hospitolservice, never()).updateHospitol(any(Hospitol.class));
	    }
	    
	    @Test
	    public void testUpdateHospitol_HospitalNotFound_Returns404() throws Exception {
	        Hospitol hospitol = new Hospitol(99L, "Nonexistent Hospital", "Unknown", "unknown@gmail.com", 1111111111L);

	        when(hospitolservice.updateHospitol(any(Hospitol.class))).thenThrow(new IDNotFoundException("Hospital not found"));

	        mockMvc.perform(post("/user/hospitol/update")
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(hospitol)))
	                .andExpect(status().isNotFound()); // Expect 404 Not Found

	        verify(hospitolservice, times(1)).updateHospitol(any(Hospitol.class));
	    }
	    @Test
	    public void testGetHospitolByName_Success() throws Exception {
	        String hospitalName = "Apollo Hospital";
	        Hospitol hospitol = new Hospitol(1L, hospitalName, "Chennai", "apollo@gmail.com", 1234567890L);

	        // Mock service response
	        when(hospitolservice.getHospitolByName(hospitalName)).thenReturn(hospitol);

	        // Perform request and validate response
	        mockMvc.perform(post("/user/hospitol/getHospitolbyName/{hospitolname}", hospitalName) // Ensure the endpoint matches
	                .header("X-User-Role", "ADMIN")  // Providing an authorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())  // Expect 200 OK
	                .andExpect(jsonPath("$.hospitolName").value(hospitalName)) // Validate response
	                .andExpect(jsonPath("$.hospitolAddress").value("Chennai"))
	                .andExpect(jsonPath("$.emailId").value("apollo@gmail.com"));

	        // Verify that service method was called
	        verify(hospitolservice, times(1)).getHospitolByName(hospitalName);
	    }
	    @Test
	    public void testGetHospitolByName_UnauthorizedAccess_Returns403() throws Exception {
	        String hospitalName = "Apollo Hospital";

	        // Perform request with unauthorized role
	        mockMvc.perform(post("/user/hospitol/getHospitolbyName/{hospitolname}", hospitalName)
	                .header("X-User-Role", "GUEST")  // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // Expect 403 Forbidden

	        // Verify that service method was never called
	        verify(hospitolservice, never()).getHospitolByName(anyString());
	    }
	}

	
