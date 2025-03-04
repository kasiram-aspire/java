package com.Blood.Inventory_Service.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.Blood.Inventory_Service.Exception.DataAlreadyPresent;
import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;
import com.Blood.Inventory_Service.Repository.BloodCountRepository;
import com.Blood.Inventory_Service.Repository.InventroyRepo;
import com.Blood.Inventory_Service.Service.InventoryService;
import com.Blood.Inventory_Service.dto.Donordto;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
	@InjectMocks
	  private InventoryService inventoryservice;
	@Mock
	 private InventroyRepo inventoryrepo;
	 @Mock
	    private WebClient.Builder webClientBuilder;

	    @Mock
	    private WebClient webClient;
	    @Mock
	    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	    @Mock
	    private WebClient.RequestHeadersSpec requestHeadersSpec;
        @Mock
        private BloodCountRepository bloodcountrepo;
	    @Mock
	    private WebClient.ResponseSpec responseSpec;
	    @BeforeEach
	    void setUp() {
	        lenient().when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
	        lenient().when(webClientBuilder.build()).thenReturn(webClient);
	        
	    }
	    @Test
	    public void testAddDonorToTheInventory_Success() {
	        String donorName = "John Doe";
	        Long units = 2L;
	        LocalDate givenDate = LocalDate.of(2024, 3, 4);

	        // Mock Inventory Repository (Donor not present)
	        when(inventoryrepo.findByDonorname(donorName)).thenReturn(null);

	        // Mock WebClient response (User Service call)
	        Donordto mockDonorDto = new Donordto();
	        mockDonorDto.setAge(30);
	        mockDonorDto.setBloodGroup("O+");

	        when(webClientBuilder.build()).thenReturn(webClient);
	        when(webClient.get()).thenReturn(requestHeadersUriSpec);
	        when(requestHeadersUriSpec.uri("http://USER-SERVICE/user/donor/getByName/" + donorName)).thenReturn(requestHeadersSpec);
	        when(requestHeadersSpec.header(eq("X-User-Role"), eq("ADMIN"))).thenReturn(requestHeadersSpec);
	        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
	        when(responseSpec.bodyToMono(Donordto.class)).thenReturn(Mono.just(mockDonorDto));

	        // Mock BloodCount Repository
	        when(bloodcountrepo.findByBloodGroup("O+")).thenReturn(null);

	        // Call method under test
	        Inventory result = inventoryservice.addDonorToTheInventory(donorName, units, givenDate);

	        // Verify the results
	        assertNotNull(result);
	        assertEquals(donorName, result.getDonorname());
	        assertEquals(units, result.getUnitOfBlood());
	        assertEquals("O+", result.getBloodGroup());
	        assertEquals(30, result.getAge());

	        // Verify interactions
	        verify(inventoryrepo, times(1)).save(any(Inventory.class));
	        verify(bloodcountrepo, times(1)).save(any(BloodCount.class));
	    }
	    @Test
	    public void testAddDonorToTheInventory_DonorAlreadyExists() {
	        String donorName = "John Doe";
	        Long units = 2L;
	        LocalDate givenDate = LocalDate.of(2024, 3, 4);

	        // Mock Inventory Repository (Donor already present)
	        when(inventoryrepo.findByDonorname(donorName)).thenReturn(new Inventory());

	        // Expect exception when trying to add duplicate donor
	        DataAlreadyPresent exception = assertThrows(DataAlreadyPresent.class, () ->
	                inventoryservice.addDonorToTheInventory(donorName, units, givenDate));

	        assertEquals("the name{John Doe} already present", exception.getMessage());

	        // Verify no interactions with external calls
	        verify(webClientBuilder, never()).build();
	        verify(bloodcountrepo, never()).findByBloodGroup(anyString());
	    }
	    @Test
	    public void testAddDonorToTheInventory_BloodGroupAlreadyExists() {
	        String donorName = "John Doe";
	        Long units = 2L;
	        LocalDate givenDate = LocalDate.of(2024, 3, 4);

	        // Mock Inventory Repository (Donor not present)
	        when(inventoryrepo.findByDonorname(donorName)).thenReturn(null);

	        // Mock WebClient response (User Service call)
	        Donordto mockDonorDto = new Donordto();
	        mockDonorDto.setAge(30);
	        mockDonorDto.setBloodGroup("O+");

	        when(webClientBuilder.build()).thenReturn(webClient);
	        when(webClient.get()).thenReturn(requestHeadersUriSpec);
	        when(requestHeadersUriSpec.uri("http://USER-SERVICE/user/donor/getByName/" + donorName)).thenReturn(requestHeadersSpec);
	        when(requestHeadersSpec.header(eq("X-User-Role"), eq("ADMIN"))).thenReturn(requestHeadersSpec);
	        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
	        when(responseSpec.bodyToMono(Donordto.class)).thenReturn(Mono.just(mockDonorDto));

	        // Mock BloodCount Repository (Blood group already exists)
	        BloodCount existingBloodCount = new BloodCount();
	        existingBloodCount.setBloodGroup("O+");
	        existingBloodCount.setUnits(5L);

	        when(bloodcountrepo.findByBloodGroup("O+")).thenReturn(existingBloodCount);

	        // Call method under test
	        Inventory result = inventoryservice.addDonorToTheInventory(donorName, units, givenDate);

	        // Verify the results
	        assertNotNull(result);
	        assertEquals(donorName, result.getDonorname());
	        assertEquals(units, result.getUnitOfBlood());
	        assertEquals("O+", result.getBloodGroup());
	        assertEquals(30, result.getAge());

	        // Verify blood count update
	        assertEquals(7L, existingBloodCount.getUnits());

	        // Verify interactions
	        verify(inventoryrepo, times(1)).save(any(Inventory.class));
	        verify(bloodcountrepo, times(1)).save(existingBloodCount);
	    }
	    @Test
	    public void testGetDonorFromInventory_ReturnsDonors() {
	        // Mock inventory data
	        Inventory donor1 = new Inventory(1L, "kasiram", "O+ve", 30,2L,LocalDate.of(2024, 3, 4));
	        Inventory donor2 = new Inventory(2L, "kasiram", "A+ve", 25, 1L,LocalDate.of(2024, 3, 3));

	        List<Inventory> mockInventoryList = Arrays.asList(donor1, donor2);

	        // Mock repository behavior
	        when(inventoryrepo.findAll()).thenReturn(mockInventoryList);

	        // Call the method
	        List<Inventory> result = inventoryservice.getDonorFromInventroy();

	        // Verify the results
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("kasiram", result.get(0).getDonorname());
	        assertEquals("kasiram", result.get(1).getDonorname());

	        // Verify repository interaction
	        verify(inventoryrepo, times(1)).findAll();
	    }
	    @Test
	    public void testGetDonorFromInventory_ReturnsEmptyList() {
	        // Mock empty inventory list
	        when(inventoryrepo.findAll()).thenReturn(Collections.emptyList());

	        // Call the method
	        List<Inventory> result = inventoryservice.getDonorFromInventroy();

	        // Verify the results
	        assertNotNull(result);
	        assertTrue(result.isEmpty());

	        // Verify repository interaction
	        verify(inventoryrepo, times(1)).findAll();
	    }
	    @Test
	    public void testGetTotalAmountOfBlood_ReturnsBloodCounts() {
	        // Mock blood count data
	        BloodCount blood1 = new BloodCount(1L, "O+ve", 5L);
	        BloodCount blood2 = new BloodCount(2L, "A+ve", 3L);

	        List<BloodCount> mockBloodCountList = Arrays.asList(blood1, blood2);

	        // Mock repository behavior
	        when(bloodcountrepo.findAll()).thenReturn(mockBloodCountList);

	        // Call the method
	        List<BloodCount> result = inventoryservice.getTotalAmountOfBlood();

	        // Verify the results
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("O+ve", result.get(0).getBloodGroup());
	        assertEquals(5L, result.get(0).getUnits());
	        assertEquals("A+ve", result.get(1).getBloodGroup());
	        assertEquals(3L, result.get(1).getUnits());
	        // Verify repository interaction
	        verify(bloodcountrepo, times(1)).findAll();
	    }
	    @Test
	    public void testGetTotalAmountOfBlood_ReturnsEmptyList() {
	        // Mock empty blood count list
	        when(bloodcountrepo.findAll()).thenReturn(Collections.emptyList());

	        // Call the method
	        List<BloodCount> result =inventoryservice.getTotalAmountOfBlood();

	        // Verify the results
	        assertNotNull(result);
	        assertTrue(result.isEmpty());

	        // Verify repository interaction
	        verify(bloodcountrepo, times(1)).findAll();
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_ReturnsBloodCount() {
	        // Mock blood count data
	        BloodCount mockBloodCount = new BloodCount(1L, "O+", 5L);

	        // Mock repository behavior
	        when(bloodcountrepo.findByBloodGroup("O+")).thenReturn(mockBloodCount);

	        // Call the method
	        BloodCount result = inventoryservice.getCountBasedOnBloodGroup("O+");

	        // Verify the results
	        assertNotNull(result);
	        assertEquals("O+", result.getBloodGroup());
	        assertEquals(5L, result.getUnits());

	        // Verify repository interaction
	        verify(bloodcountrepo, times(1)).findByBloodGroup("O+");
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_BloodGroupNotFound() {
	        // Mock repository returning null for a non-existing blood group
	        when(bloodcountrepo.findByBloodGroup("A+ve")).thenReturn(null);

	        // Call the method
	        BloodCount result = inventoryservice.getCountBasedOnBloodGroup("A+ve");

	        // Verify the results
	        assertNull(result);

	        // Verify repository interaction
	        verify(bloodcountrepo, times(1)).findByBloodGroup("A+ve");
	    }
	    @Test
	    public void testDeleteUserFromInventory_UserExists_ReturnsTrue() {
	        // Mock inventory data
	        Inventory mockInventory = new Inventory(1L, "kasiram","O+ve",23, 2L,LocalDate.of(2024, 3, 4));
	        BloodCount mockBloodCount = new BloodCount();
	        mockBloodCount.setBloodGroup("O+ve");
	        mockBloodCount.setUnits(5L);

	        // Mock repository behavior
	        when(inventoryrepo.findByDonorname("kasiram")).thenReturn(mockInventory);
	        when(bloodcountrepo.findByBloodGroup("O+ve")).thenReturn(mockBloodCount);

	        // Call the method
	        boolean result = inventoryservice.deleteUserFromInventory("kasiram");

	        // Verify the results
	        assertTrue(result);
	        assertEquals(3L, mockBloodCount.getUnits()); // Check if blood count is decremented correctly

	        // Verify repository interactions
	        verify(inventoryrepo, times(1)).findByDonorname("kasiram");
	        verify(bloodcountrepo, times(1)).findByBloodGroup("O+ve");
	        verify(bloodcountrepo, times(1)).save(mockBloodCount);
	        verify(inventoryrepo, times(1)).delete(mockInventory);
	    }
	    @Test
	    public void testDeleteUserFromInventory_UserDoesNotExist_ReturnsFalse() {
	        // Mock repository returning null (donor not found)
	        when(inventoryrepo.findByDonorname("kasiram")).thenReturn(null);

	        // Call the method
	        boolean result = inventoryservice.deleteUserFromInventory("kasiram");

	        // Verify the results
	        assertFalse(result);

	        // Verify repository interactions
	        verify(inventoryrepo, times(1)).findByDonorname("kasiram");
	        verify(inventoryrepo, never()).delete(any(Inventory.class)); // Ensure delete is not called
	    }
}
