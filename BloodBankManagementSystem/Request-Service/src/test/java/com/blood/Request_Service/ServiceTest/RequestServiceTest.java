package com.blood.Request_Service.ServiceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.blood.Request_Service.Exception.IDNotFoundException;
import com.blood.Request_Service.Model.Request;
import com.blood.Request_Service.Model.Status;
import com.blood.Request_Service.Repository.RequestRepository;
import com.blood.Request_Service.Repository.StatusRepository;
import com.blood.Request_Service.Service.RequestService;
import com.blood.Request_Service.dto.Donordto;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
	 @InjectMocks
	 RequestService requestservice;
	 @Mock
	 RequestRepository requestrepo;
	 @Mock
	 StatusRepository statusrepo;
	  @Mock
	    private WebClient.Builder webClientBuilder;

	    @Mock
	    private WebClient webClient;

	    @Mock
	    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

	    @Mock
	    private WebClient.ResponseSpec responseSpec;
	  
	    @BeforeEach
	    void setUp() {
	        lenient().when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
	        lenient().when(webClientBuilder.build()).thenReturn(webClient);
	        
	    }
	    @Test
	    public void testAddRequestFromHospital_Success() {
	    	
	        Request request = new Request(1L, "Apollo Hospital", "O+ve","we need blod less tha 23 age", LocalDate.of(2024, 3, 5));

	        when(requestrepo.save(any(Request.class))).thenReturn(request);
	        
	        Request savedRequest = requestservice.addRequestFromHospitol(request);
	        
	        assertNotNull(savedRequest);
	        assertEquals(1L, savedRequest.getId());
	        assertEquals("Apollo Hospital", savedRequest.getHospitolName());
	        assertEquals("O+ve", savedRequest.getBloodGroup());
	        assertEquals("we need blod less tha 23 age", savedRequest.getMessage());
	        assertEquals(LocalDate.of(2024, 3, 5), savedRequest.getDatelimit());
	        verify(requestrepo, times(1)).save(request);
	    }
	    @Test
	    public void testGetAllHospitalRequests_Success() {
	        List<Request> mockRequests = Arrays.asList(
	        		new Request(1L, "Apollo Hospital", "O+ve","we need blod less tha 23 age", LocalDate.of(2024, 3, 5)),
	        		new Request(2L, "Anna Hospital", "O+ve","we need blod less tha 28 age", LocalDate.of(2024, 3, 10)));
	        

	        when(requestrepo.findAll()).thenReturn(mockRequests);

	        List<Request> requests = requestservice.getAllHospitolRequest();

	        assertNotNull(requests);
	        assertEquals(2, requests.size());

	        assertEquals(1L, requests.get(0).getId());
	        assertEquals("Apollo Hospital", requests.get(0).getHospitolName());
	        assertEquals("O+ve", requests.get(0).getBloodGroup());
	        assertEquals("we need blod less tha 23 age", requests.get(0).getMessage());
	        assertEquals(LocalDate.of(2024, 3, 5), requests.get(0).getDatelimit());

	        assertEquals(2L, requests.get(1).getId());
	        assertEquals("Anna Hospital", requests.get(1).getHospitolName());
	        assertEquals("O+ve", requests.get(1).getBloodGroup());
	        assertEquals("we need blod less tha 28 age", requests.get(1).getMessage());
	        assertEquals(LocalDate.of(2024, 3, 10), requests.get(1).getDatelimit());
	        
	        verify(requestrepo, times(1)).findAll();

	    }
	    @Test
	    public void testGetAllHospitalRequests_EmptyList() {
	        when(requestrepo.findAll()).thenReturn(Collections.emptyList());

	        List<Request> requests = requestservice.getAllHospitolRequest();

	        assertNotNull(requests);
	        assertTrue(requests.isEmpty());

	        verify(requestrepo, times(1)).findAll();
	    }
	    @Test
	    public void testGetRequestByDate_Success() {
	        // Given
	        LocalDate requestDate = LocalDate.of(2024, 3, 5);
	        List<Request> mockRequests = Arrays.asList(
	        		new Request(1L, "Apollo Hospital", "O+ve","we need blod less tha 23 age", LocalDate.of(2024, 3, 5)),
	        		new Request(2L, "Anna Hospital", "O+ve","we need blod less tha 28 age", LocalDate.of(2024, 3, 5)));
	        

	        when(requestrepo.findByDatelimit(requestDate)).thenReturn(mockRequests);

	        // When
	        List<Request> requests = requestservice.getRequestByDate(requestDate);

	        // Then
	        assertNotNull(requests);
	        assertEquals(2, requests.size());

	        assertEquals(1L, requests.get(0).getId());
	        assertEquals("Apollo Hospital", requests.get(0).getHospitolName());
	        assertEquals("O+ve", requests.get(0).getBloodGroup());
	        assertEquals("we need blod less tha 23 age", requests.get(0).getMessage());
	        assertEquals(requestDate, requests.get(0).getDatelimit());

	        assertEquals(2L, requests.get(1).getId());
	        assertEquals("Anna Hospital", requests.get(1).getHospitolName());
	        assertEquals("O+ve", requests.get(1).getBloodGroup());
	        assertEquals("we need blod less tha 28 age", requests.get(1).getMessage());
	        assertEquals(requestDate, requests.get(1).getDatelimit());

	        verify(requestrepo, times(1)).findByDatelimit(requestDate);
	    }
	    @Test
	    public void testGetRequestByDate_NotFound() {
	        // Given
	        LocalDate requestDate = LocalDate.of(2024, 3, 10);

	        when(requestrepo.findByDatelimit(requestDate)).thenReturn(Collections.emptyList());

	        // When & Then
	        IDNotFoundException exception = assertThrows(IDNotFoundException.class, () -> {
	            requestservice.getRequestByDate(requestDate);
	        });

	        assertEquals("In this Date: 2024-03-10 no data is found", exception.getMessage());

	        verify(requestrepo, times(1)).findByDatelimit(requestDate);
	    }
	    @Test
	    public void testSetTheRequest_Success() {
	        // Given
	        String donorName = "John Doe";
	        String requestStatus = "Approved";
	        String hospitalName = "Apollo Hospital";

	        Donordto mockDonor = new Donordto(1L,donorName,30,"O+ve","kasiram186@gmial.com",9952563951L,LocalDate.of(2024, 3, 5));
	        Status mockStatus = new Status(1L, hospitalName,donorName,"SUCCESS", 30,"O+ve");

	        // Mock WebClient call to get donor details
	        when(webClientBuilder.build().get()
	                .uri("http://USER-SERVICE/user/donor/getByName/" + donorName)
	                .retrieve()
	                .bodyToMono(Donordto.class))
	                .thenReturn(Mono.just(mockDonor));

	        // Mock WebClient call to delete donor from inventory
	        when(webClientBuilder.build().post()
	                .uri("http://INVENTORY-SERVICE/inventroy/deleteUserFromInventory/" + donorName)
	                .header("X-User-Role", "ADMIN")
	                .retrieve()
	                .bodyToMono(String.class))
	                .thenReturn(Mono.just("Inventory with name 'John Doe' deleted successfully."));

	        when(statusrepo.save(any(Status.class))).thenReturn(mockStatus);

	        // When
	        Status result = requestservice.setTheRequest(donorName, requestStatus, hospitalName);

	        // Then
	        assertNotNull(result);
	        assertEquals(donorName, result.getDonorName());
	        assertEquals("O+ve", result.getBloodGroup());
	        assertEquals(30, result.getAge());
	        assertEquals(hospitalName, result.getHospitolname());
	        assertEquals(requestStatus, result.getStatus());
	        verify(statusrepo, times(1)).save(any(Status.class));
	    }

}
