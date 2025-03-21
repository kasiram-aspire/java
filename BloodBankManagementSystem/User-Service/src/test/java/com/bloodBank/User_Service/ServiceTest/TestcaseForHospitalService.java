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
public class TestcaseForHospitalService {

    @InjectMocks
    HospitolService hospitalservice;

    @Mock
    HospitolRepository hospitalrepo;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private Hospitol hospital;

    @BeforeEach
    void setUp() {
        lenient().when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        lenient().when(webClientBuilder.build()).thenReturn(webClient);

        // Corrected instance variable initialization
        hospital = new Hospitol();
        hospital.setId(1L);
        hospital.setHospitolName("Apollo Hospital");
        hospital.setHospitolAddress("Chennai, India");
        hospital.setEmailId("apollo@gmail.com");
        hospital.setPhoneno(9876543210L);
    }

    @Test
    void testAddHospitol_Success() {
        // Mock behavior: No existing hospital found, so it should save
        when(hospitalrepo.findByHospitolName(hospital.getHospitolName())).thenReturn(null);
        when(hospitalrepo.save(hospital)).thenReturn(hospital);

        Hospitol savedHospitol = hospitalservice.addHospitol(hospital);
        assertNotNull(savedHospitol);  
        // Fix: Ensure expected and actual values match
        assertEquals("Apollo Hospital",savedHospitol.getHospitolName());
        verify(hospitalrepo, times(1)).save(hospital); // Verify that save was called once
    }
    @Test
    void testAddHospitol_Duplicate_ThrowsException() {
        // Mock behavior: The hospital already exists
        when(hospitalrepo.findByHospitolName(hospital.getHospitolName())).thenReturn(hospital);

        // Expect exception
        DataAlreadyPresent exception = assertThrows(DataAlreadyPresent.class, () -> {
            hospitalservice.addHospitol(hospital);
        });
        assertTrue(exception.getMessage().contains("The donor:Apollo Hospitalis already registered in db"));
        verify(hospitalrepo, never()).save(any(Hospitol.class)); // Verify that save was NOT called
    }
    @Test
    public void testGetHospitolDetails_WithRecords() {

        List<Hospitol> hospitolList = new ArrayList<>();
        hospitolList.add(new Hospitol(1L, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L));
        hospitolList.add(new Hospitol(2L, "Fortis Hospital", "Bangalore","Fortis@gmail.com",9876543210L));
        when(hospitalrepo.findAll()).thenReturn(hospitolList);
        List<Hospitol> result = hospitalservice.getHospitolDetails();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(hospitolList, result);
        // Verify repo interaction
        verify(hospitalrepo, times(1)).findAll();
    }
    @Test
    public void testGetHospitolDetails_EmptyDatabase() {
        //  No hospitals in the database
        when(hospitalrepo.findAll()).thenReturn(Collections.emptyList());
        List<Hospitol> result = hospitalservice.getHospitolDetails();
        assertNotNull(result);
        assertEquals(0, result.size()); //check the repo is empty
        // Verify repo interaction
        verify(hospitalrepo, times(1)).findAll();
    }
    @Test
    public void testGetById_HospitalFound() {
        // Mock an existing hospital
        Long hospitalId = 1L;
        Hospitol hospitol=new Hospitol(hospitalId, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);
        when(hospitalrepo.findById(hospitalId)).thenReturn(Optional.of(hospitol));

        // Act
        Hospitol result = hospitalservice.getById(hospitalId);

        // Assert
        assertNotNull(result);
        assertEquals(hospitalId, result.getId());
        assertEquals("Apollo Hospital", result.getHospitolName());

        // Verify interaction
        verify(hospitalrepo, times(2)).findById(hospitalId); // Method calls findById twice
    }

    @Test
    public void testGetById_HospitalNotFound() {
        //  No hospital found
        Long invalidId = 99L;
        when(hospitalrepo.findById(invalidId)).thenReturn(Optional.empty());
        //  Expect an exception
        IDNotFoundException exception = assertThrows(IDNotFoundException.class, () -> hospitalservice.getById(invalidId));
        assertEquals("The id:99not found in db", exception.getMessage());

        // Verify interaction
        verify(hospitalrepo, times(1)).findById(invalidId);
    }
    @Test
    public void testUpdateHospitol_Success() {
        // Arrange: Mock an existing hospital
        Long hospitalId = 1L;
        Hospitol existingHospitol = new Hospitol(hospitalId, "Apollo Hospital", "Chennai","apollo@gmail.com",1234567890L);
        Hospitol updatedHospitol = new Hospitol(hospitalId, "Fortis Hospital", "Bangalore","fortis@gmail.com",9876543210L);
        
        when(hospitalrepo.findById(hospitalId)).thenReturn(Optional.of(existingHospitol));
        when(hospitalrepo.save(any(Hospitol.class))).thenReturn(updatedHospitol);
        Hospitol result = hospitalservice.updateHospitol(updatedHospitol);
        assertNotNull(result);
        assertEquals("Fortis Hospital", result.getHospitolName());
        assertEquals("Bangalore", result.getHospitolAddress());
        assertEquals(9876543210L, result.getPhoneno());
        assertEquals("fortis@gmail.com", result.getEmailId());
        // Verify interactions
        verify(hospitalrepo, times(1)).findById(hospitalId);
        verify(hospitalrepo, times(1)).save(existingHospitol);
    }
    @Test
    public void testUpdateHospitol_NotFound_ThrowsException() {
        // No hospital found
        Long invalidId = 99L;
        Hospitol updatedHospitol = new Hospitol(invalidId, "Fortis Hospital", "Bangalore","fortis@gmail.com",9876543210L);

        when(hospitalrepo.findById(invalidId)).thenReturn(Optional.empty());

        //  exception
        IDNotFoundException exception = assertThrows(IDNotFoundException.class, () -> hospitalservice.updateHospitol(updatedHospitol));
        assertEquals("The id:99not found in db", exception.getMessage());

        // Verify interactions
        verify(hospitalrepo, times(1)).findById(invalidId);
        verify(hospitalrepo, never()).save(any(Hospitol.class));
    }
    @Test
    public void testGetHospitolByName_Success() {
        // Mock a hospital
        String hospitalName = "Apollo Hospital";
        Hospitol mockHospital = new Hospitol(1L, hospitalName, "Chennai","apollo@gmail.com",1234567890L);

        when(hospitalrepo.findByHospitolName(hospitalName)).thenReturn(mockHospital);

        Hospitol result = hospitalservice.getHospitolByName(hospitalName);

        assertNotNull(result);
        assertEquals(hospitalName, result.getHospitolName());
        assertEquals("Chennai", result.getHospitolAddress());
        assertEquals(1234567890L, result.getPhoneno());
        assertEquals("apollo@gmail.com", result.getEmailId());

        // Verify interactions
        verify(hospitalrepo, times(1)).findByHospitolName(hospitalName);
    }

    @Test
    public void testGetHospitolByName_NotFound_ThrowsException() {
        // No hospital found
        String invalidHospitalName = "Unknown Hospital";

        when(hospitalrepo.findByHospitolName(invalidHospitalName)).thenReturn(null);

        //  Expect an exception
        DataAlreadyPresent exception = assertThrows(DataAlreadyPresent.class, () -> hospitalservice.getHospitolByName(invalidHospitalName));
        assertEquals("The Unknown Hospitalis Not present in data base", exception.getMessage());

        // Verify interactions
        verify(hospitalrepo, times(1)).findByHospitolName(invalidHospitalName);
    }
    
}

