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

import java.time.LocalDate;
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
import com.bloodBank.User_Service.Exceptions.DonorNotFoundException;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Repo.DonorsRepository;
import com.bloodBank.User_Service.Service.DonorService;

@ExtendWith(MockitoExtension.class)
public class DonorServiceTest {

	 @InjectMocks
	    private DonorService donorService;

	    @Mock
	    private DonorsRepository donorrepo;

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

//	    @Test
//	    public void addDonorShouldSuccessfullyAdded() {
//	        Donors newDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
//
//	        // Only stub if 'findByDonorName' is actually called in 'addDonors()'
//	         when(donorrepo.findByDonorName(newDonor.getDonorName())).thenReturn(null);
//
//	        when(donorrepo.save(any(Donors.class))).thenReturn(newDonor);
//
//	        // MOCK WEB CLIENT ONLY IF addDonors() USES IT
//	        lenient().when(webClient.post()).thenReturn(requestBodyUriSpec);
//	        lenient().when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodyUriSpec);
//	        lenient().when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
//	        lenient().when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Email Sent"));
//	        String emailResponse = responseSpec.bodyToMono(String.class).block();
//	        // Call the method
//	        Donors donorAdded = donorService.addDonors(newDonor);
//            // verify email sent
//	        assertEquals("Email Sent", emailResponse);
//	        // Verify the donor is saved
//	        assertNotNull(donorAdded);
//	    }
//	    
        @Test
	    public void addDonorAlreadyPresent()
	    {
	    	  Donors newDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
	    	  when(donorrepo.findByDonorName(newDonor.getDonorName())).thenReturn(newDonor);
	    	  assertThrows(DataAlreadyPresent.class,() -> donorService.addDonors(newDonor));
	    	  verify(donorrepo, never()).save(any(Donors.class));	  
	    }
	    @Test
	    public void getALLDonorsDetails()
	    {
	    	      List<Donors> donorlist=new ArrayList<>();
	    	      Donors newDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
	    	      donorlist.add(newDonor);
	    	      newDonor = new Donors(1L, "Karthik", 23, "B+ve", "karthik186@gmail", 9952563951L, LocalDate.of(2001, 5, 17));
	    	      donorlist.add(newDonor);
	              when(donorrepo.findAll()).thenReturn(donorlist);
	              List<Donors> result = donorService.getDonors();
	              assertEquals((short)2,(short) result.size());
	              assertEquals(donorlist, result);
	    }
	    @Test
	    public void getDonorById()
	    {
	    	 Donors newDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
	    	 when(donorrepo.findById(newDonor.getId())).thenReturn(Optional.of(newDonor));
	    	 Donors existingDonor=donorService.getById(1L);
	    	 assertNotNull(existingDonor);
	    	 assertEquals(existingDonor,newDonor);
	    	 
	    }
	    @Test
	    public void getDonorByIdIfDonorNotPresent()
	    {
	    	Long InvalidId=2L;
	    	when(donorrepo.findById(InvalidId)).thenReturn(Optional.empty());
	         assertThrows(IDNotFoundException.class,()->donorService.getById(InvalidId));
	         
	    }
	    @Test
	    public void updateExistingDonor()
	    {
	    	 Donors existingDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
	    	when(donorrepo.findById(existingDonor.getId())).thenReturn(Optional.of(existingDonor));
	    	Donors updatedExistingDonor= new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563952L, LocalDate.of(2001, 4, 17));
	    	when( donorrepo.save(existingDonor)).thenReturn(updatedExistingDonor);
	    	Donors updatedDonor=donorService.update(existingDonor);
	    	assertNotNull(updatedDonor);
	    	assertEquals(updatedExistingDonor.getPhoneno(), updatedDonor.getPhoneno());
	    	 // Verify interactions
	        verify(donorrepo, times(1)).findById(existingDonor.getId());
	        verify(donorrepo, times(1)).save(any(Donors.class)); 	
	    	  
	    }
	    @Test
	     public void DonorNotPresentForUpdating()
	     {
	    	Donors existingDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
	    	when(donorrepo.findById(existingDonor.getId())).thenReturn(Optional.empty());
	    	assertThrows(IDNotFoundException.class,()->donorService.update(existingDonor));
	    	verify(donorrepo,never()).save(any(Donors.class));
	     }
	    @Test
	    public void getDonorByBloodGroupName()
	    {
	    	String bloodgroup="O+ve";
	    	 List<Donors> donorlist=new ArrayList<>();
   	      Donors donor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
   	      donorlist.add(donor);
   	      donor = new Donors(1L, "Karthik", 23, "O+ve", "karthik186@gmail", 9952563951L, LocalDate.of(2001, 5, 17));
   	      donorlist.add(donor);
	      when(donorrepo.findByBloodGroup(bloodgroup)).thenReturn(donorlist);
	      List<Donors>matchedDonors=donorService.getDonorByBloodGroupName(bloodgroup);
	      assertNotNull(matchedDonors);
	      assertEquals(donorlist,matchedDonors);
	      // verify repo call
	      verify(donorrepo, times(1)).findByBloodGroup(bloodgroup);
	      
	    }
	    @Test
	    public void DonorBloodGroupNotMatched()
	    {
	    	String bloodgroup="A+ve";
	    	 List<Donors> donorlist=new ArrayList<>();
  	      Donors donor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail", 9952563951L, LocalDate.of(2001, 4, 17));
  	      donorlist.add(donor);
  	      donor = new Donors(1L, "Karthik", 23, "O+ve","karthik186@gmail", 9952563951L, LocalDate.of(2001, 5, 17));
  	      donorlist.add(donor);
	      when(donorrepo.findByBloodGroup(bloodgroup)).thenReturn(Collections.emptyList());
	      assertThrows(DonorNotFoundException.class,()-> donorService.getDonorByBloodGroupName(bloodgroup));
	      // Verify interaction
	      verify(donorrepo, times(1)).findByBloodGroup(bloodgroup);
	    }
	    @Test
	    public void testGetDonorByBloodGroupNameAndAge_Success() {
	        String bloodGroup = "O+ve";
	        int minAge = 25;

	        List<Donors> donorList = new ArrayList<>();
	        donorList.add(new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)));
	        donorList.add(new Donors(2L, "Karthik", 26, "O+ve", "karthik186@gmail.com", 9952563952L, LocalDate.of(1998, 5, 17)));
	        donorList.add(new Donors(3L, "Raj", 30, "O+ve", "raj186@gmail.com", 9952563953L, LocalDate.of(1994, 3, 15)));

	        when(donorrepo.findByBloodGroup(bloodGroup)).thenReturn(donorList);

	        List<Donors> result = donorService.getDonorByBloodGroupNameAndAge(bloodGroup, minAge);

	        assertNotNull(result);
	        assertEquals(2, result.size()); // Only Karthik (26) and Raj (30) should be returned
	        assertTrue(result.stream().allMatch(donor -> donor.getAge() >= minAge));
	        
	        // Verify interaction
	        verify(donorrepo, times(1)).findByBloodGroup(bloodGroup);
	    }
	    
	    @Test
	    public void testGetDonorByBloodGroupNameAndAge_BloodGroupNotFound() {
	        String bloodGroup = "A+ve";
	        int minAge = 25;

	        when(donorrepo.findByBloodGroup(bloodGroup)).thenReturn(new ArrayList<>());

	        assertThrows(DonorNotFoundException.class, () -> donorService.getDonorByBloodGroupNameAndAge(bloodGroup, minAge));

	        // Verify interaction
	        verify(donorrepo, times(1)).findByBloodGroup(bloodGroup);
	    }

	    @Test
	    public void testGetDonorByBloodGroupNameAndAge_NoMatchingAge() {
	        String bloodGroup = "O+ve";
	        int minAge = 40;

	        List<Donors> donorList = new ArrayList<>();
	        donorList.add(new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)));
	        donorList.add(new Donors(2L, "Karthik", 26, "O+ve", "karthik186@gmail.com", 9952563952L, LocalDate.of(1998, 5, 17)));

	        when(donorrepo.findByBloodGroup(bloodGroup)).thenReturn(donorList);

	        List<Donors> result = donorService.getDonorByBloodGroupNameAndAge(bloodGroup, minAge);

	        assertNotNull(result);
	        assertTrue(result.isEmpty()); // No donor meets the age condition
	        // Verify interaction
	        verify(donorrepo, times(1)).findByBloodGroup(bloodGroup);
	    }
	    
	     
}
