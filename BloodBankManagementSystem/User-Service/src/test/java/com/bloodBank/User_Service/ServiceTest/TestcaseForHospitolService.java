package com.bloodBank.User_Service.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.bloodBank.User_Service.Exceptions.DataAlreadyPresent;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Repo.HospitolRepository;
import com.bloodBank.User_Service.Service.HospitolService;

@ExtendWith(MockitoExtension.class)
public class TestcaseForHospitolService {

    @InjectMocks
    HospitolService hospitolservice;

    @Mock
    HospitolRepository hospitolrepo;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private Hospitol hospitol;

    @BeforeEach
    void setUp() {
        lenient().when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        lenient().when(webClientBuilder.build()).thenReturn(webClient);

        // Corrected instance variable initialization
        hospitol = new Hospitol();
        hospitol.setId(1L);
        hospitol.setHospitolName("Apollo Hospital");
        hospitol.setHospitolAddress("Chennai, India");
        hospitol.setEmailId("apollo@gmail.com");
        hospitol.setPhoneno(9876543210L);
    }

    @Test
    void testAddHospitol_Success() {
        // Mock behavior: No existing hospital found, so it should save
        when(hospitolrepo.findByHospitolName(hospitol.getHospitolName())).thenReturn(null);
        when(hospitolrepo.save(hospitol)).thenReturn(hospitol);

        Hospitol savedHospitol = hospitolservice.addHospitol(hospitol);
        assertNotNull(savedHospitol);  
        // Fix: Ensure expected and actual values match
        assertEquals("Apollo Hospital",savedHospitol.getHospitolName());
        verify(hospitolrepo, times(1)).save(hospitol); // Verify that save was called once
    }
    @Test
    void testAddHospitol_Duplicate_ThrowsException() {
        // Mock behavior: The hospital already exists
        when(hospitolrepo.findByHospitolName(hospitol.getHospitolName())).thenReturn(hospitol);

        // Expect exception
        DataAlreadyPresent exception = assertThrows(DataAlreadyPresent.class, () -> {
            hospitolservice.addHospitol(hospitol);
        });
        assertTrue(exception.getMessage().contains("The donor:Apollo Hospitalis already registered in db"));
        verify(hospitolrepo, never()).save(any(Hospitol.class)); // Verify that save was NOT called
    }
    @Test
    public void testGetHospitolDetails_WithRecords() {

        List<Hospitol> hospitolList = new ArrayList<>();
        hospitolList.add(new Hospitol(1L, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L));
        hospitolList.add(new Hospitol(2L, "Fortis Hospital", "Bangalore","Fortis@gmail.com",9876543210L));
        when(hospitolrepo.findAll()).thenReturn(hospitolList);
        List<Hospitol> result = hospitolservice.getHospitolDetails();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(hospitolList, result);
        // Verify repo interaction
        verify(hospitolrepo, times(1)).findAll();
    }
    @Test
    public void testGetHospitolDetails_EmptyDatabase() {
        //  No hospitals in the database
        when(hospitolrepo.findAll()).thenReturn(Collections.emptyList());
        List<Hospitol> result = hospitolservice.getHospitolDetails();
        assertNotNull(result);
        assertEquals(0, result.size()); //check the repo is empty
        // Verify repo interaction
        verify(hospitolrepo, times(1)).findAll();
    }
    @Test
    public void testGetById_HospitalFound() {
        // Mock an existing hospital
        Long hospitalId = 1L;
        Hospitol hospitol=new Hospitol(hospitalId, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);
        when(hospitolrepo.findById(hospitalId)).thenReturn(Optional.of(hospitol));

        // Act
        Hospitol result = hospitolservice.getById(hospitalId);

        // Assert
        assertNotNull(result);
        assertEquals(hospitalId, result.getId());
        assertEquals("Apollo Hospital", result.getHospitolName());

        // Verify interaction
        verify(hospitolrepo, times(2)).findById(hospitalId); // Method calls findById twice
    }

    @Test
    public void testGetById_HospitalNotFound() {
        //  No hospital found
        Long invalidId = 99L;
        when(hospitolrepo.findById(invalidId)).thenReturn(Optional.empty());
        //  Expect an exception
        IDNotFoundException exception = assertThrows(IDNotFoundException.class, () -> hospitolservice.getById(invalidId));
        assertEquals("The id:99not found in db", exception.getMessage());

        // Verify interaction
        verify(hospitolrepo, times(1)).findById(invalidId);
    }
    @Test
    public void testUpdateHospitol_Success() {
        // Arrange: Mock an existing hospital
        Long hospitalId = 1L;
        Hospitol existingHospitol = new Hospitol(hospitalId, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);
        Hospitol updatedHospitol = new Hospitol(hospitalId, "Fortis Hospital", "Bangalore","fortis@gmail.com",9876543210L);
        
        when(hospitolrepo.findById(hospitalId)).thenReturn(Optional.of(existingHospitol));
        when(hospitolrepo.save(any(Hospitol.class))).thenReturn(updatedHospitol);
        Hospitol result = hospitolservice.updateHospitol(updatedHospitol);
        assertNotNull(result);
        assertEquals("Fortis Hospital", result.getHospitolName());
        assertEquals("Bangalore", result.getHospitolAddress());
        assertEquals(9876543210L, result.getPhoneno());
        assertEquals("fortis@gmail.com", result.getEmailId());
        // Verify interactions
        verify(hospitolrepo, times(1)).findById(hospitalId);
        verify(hospitolrepo, times(1)).save(existingHospitol);
    }
    @Test
    public void testUpdateHospitol_NotFound_ThrowsException() {
        // No hospital found
        Long invalidId = 99L;
        Hospitol updatedHospitol = new Hospitol(invalidId, "Fortis Hospital", "Bangalore","fortis@gmail.com",9876543210L);

        when(hospitolrepo.findById(invalidId)).thenReturn(Optional.empty());

        //  exception
        IDNotFoundException exception = assertThrows(IDNotFoundException.class, () -> hospitolservice.updateHospitol(updatedHospitol));
        assertEquals("The id:99not found in db", exception.getMessage());

        // Verify interactions
        verify(hospitolrepo, times(1)).findById(invalidId);
        verify(hospitolrepo, never()).save(any(Hospitol.class));
    }
    @Test
    public void testGetHospitolByName_Success() {
        // Mock a hospital
        String hospitalName = "Apollo Hospital";
        Hospitol mockHospital = new Hospitol(1L, hospitalName, "Chennai","apollo@gmail.com",1234567890L);

        when(hospitolrepo.findByHospitolName(hospitalName)).thenReturn(mockHospital);

        Hospitol result = hospitolservice.getHospitolByName(hospitalName);

        assertNotNull(result);
        assertEquals(hospitalName, result.getHospitolName());
        assertEquals("Chennai", result.getHospitolAddress());
        assertEquals(1234567890L, result.getPhoneno());
        assertEquals("apollo@gmail.com", result.getEmailId());

        // Verify interactions
        verify(hospitolrepo, times(1)).findByHospitolName(hospitalName);
    }

    @Test
    public void testGetHospitolByName_NotFound_ThrowsException() {
        // No hospital found
        String invalidHospitalName = "Unknown Hospital";

        when(hospitolrepo.findByHospitolName(invalidHospitalName)).thenReturn(null);

        //  Expect an exception
        DataAlreadyPresent exception = assertThrows(DataAlreadyPresent.class, () -> hospitolservice.getHospitolByName(invalidHospitalName));
        assertEquals("The donor:Unknown Hospitalis already registered in db", exception.getMessage());

        // Verify interactions
        verify(hospitolrepo, times(1)).findByHospitolName(invalidHospitalName);
    }
    
}

