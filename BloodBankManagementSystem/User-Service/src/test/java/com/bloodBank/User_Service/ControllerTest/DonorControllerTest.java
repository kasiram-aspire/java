package com.bloodBank.User_Service.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bloodBank.User_Service.Controller.DonorContoller;
import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Service.DonorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class DonorControllerTest {   

    private MockMvc mockMvc;

    @Mock
    private DonorService donorService;

    @InjectMocks
    private DonorContoller donorController;
    private ObjectMapper objectMapper = new ObjectMapper(); 
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(donorController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

//    @Test
//    public void testGetDonorsAdminRoleSuccess() throws Exception {
//        List<Donors> donors = new ArrayList<>();
//        donors.add(new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)));
//        donors.add(new Donors(2L, "Karthik", 25, "B+ve", "karthik186@gmail.com", 9952563952L, LocalDate.of(1999, 5, 20)));
//
//        when(donorService.getDonors()).thenReturn(donors);
//
//        mockMvc.perform(get("/user/donor/getDonor")   // 
//                .header("X-User-Role", "ADMIN")   // 
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())   //
//                .andExpect(jsonPath("$.length()").value(2))  
//                .andExpect(jsonPath("$[0].donorName").value("Kasiram"))
//                .andExpect(jsonPath("$[1].donorName").value("Karthik"));
//
//        verify(donorService, times(1)).getDonors();
//    }
    @Test
    public void test_GetDonors_NonAdminRole_Forbidden() throws Exception {
        mockMvc.perform(get("/user/donor/getDonor")
                .header("X-User-Role", "USER")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(donorService, never()).getDonors();
    }
    @Test
    public void test_GetDonors_EmptyList() throws Exception {
        when(donorService.getDonors()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/donor/getDonor")
                .header("X-User-Role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(donorService, times(1)).getDonors();
    }
    @Test
    public void testGetDonorById_ValidAdminRole_ShouldReturnDonor() throws Exception {
        Long donorId = 1L;
        Donors mockDonor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)); // Sample donor data

        when(donorService.getById(donorId)).thenReturn(mockDonor);

        mockMvc.perform(get("/user/donor/getDonorbyId/{id}",donorId)
                .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(donorId))
                .andExpect(jsonPath("$.donorName").value("Kasiram"))
                .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
                .andExpect(jsonPath("$.emailId").value("kasiram186@gmail.com"));

        verify(donorService, times(1)).getById(donorId);
    }
    @Test
    public void testGetDonorById_InvalidRole_ShouldReturnForbidden() throws Exception {
        Long donorId = 1L;

        mockMvc.perform(get("/user/donor/getDonorbyId/{id}", donorId)
                .header("X-User-Role", "USER"))  // Non-admin role
                .andExpect(status().isForbidden());

        verify(donorService, never()).getById(donorId);
    }
    @Test
    public void testUpdateDonor_ValidAdminRole_ShouldReturnUpdatedDonor() throws Exception {
        Donors donor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17));
        when(donorService.update(any(Donors.class))).thenReturn(donor);
        mockMvc.perform(post("/user/donor/update")
                .header("X-User-Role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.donorName").value("Kasiram"))
                .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
                .andExpect(jsonPath("$.emailId").value("kasiram186@gmail.com"));
        verify(donorService, times(1)).update(any(Donors.class));
    }
    @Test
    public void testUpdateDonor_InvalidRole_ShouldReturnForbidden() throws Exception {
        Donors donor = new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17));
        mockMvc.perform(post("/user/donor/update")
                .header("X-User-Role", "USER") // Non-admin role
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donor)))
                .andExpect(status().isForbidden());

        verify(donorService, never()).update(any(Donors.class));
    }
    @Test
    public void testGetDonorByBloodGroupName_ValidAdminRole_ShouldReturnDonors() throws Exception {
        List<Donors> donors = Arrays.asList(
            new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)),
            new Donors(2L, "Rajesh", 25, "O+ve", "rajesh@gmail.com", 9876543210L, LocalDate.of(1999, 6, 10))
        );

        when(donorService.getDonorByBloodGroupName("O+ve")).thenReturn(donors);

        mockMvc.perform(post("/user/donor/getByBloodGrouName/{Bloodgroup}", "O+ve")
                .header("X-User-Role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Expecting two donors
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].donorName").value("Kasiram"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].donorName").value("Rajesh"));

        verify(donorService, times(1)).getDonorByBloodGroupName("O+ve");
    }
    @Test
    public void testGetDonor_By_BloodGroupName_InvalidRole_Should_ReturnForbidden() throws Exception {
        mockMvc.perform(post("/user/donor/getByBloodGrouName/{Bloodgroup}", "O+ve")
                .header("X-User-Role", "USER") // Non-admin role
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(donorService, never()).getDonorByBloodGroupName(anyString());
    }
    @Test
    public void testGetDonorByBloodGroupName_NoMatchingDonors_ShouldReturnEmptyList() throws Exception {
        when(donorService.getDonorByBloodGroupName("AB-ve")).thenReturn(Collections.emptyList());
        
        mockMvc.perform(post("/user/donor/getByBloodGrouName/{Bloodgroup}","AB-ve")
                .header("X-User-Role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(donorService, times(1)).getDonorByBloodGroupName("AB-ve");
    }
    @Test
    void testGetDonorByBloodGroupNameAndAge_AdminRole_Success() throws Exception {
    	 List<Donors> donors = Arrays.asList(
    	            new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17)),
    	            new Donors(2L, "Rajesh", 25, "O+ve", "rajesh@gmail.com", 9876543210L, LocalDate.of(1999, 6, 10))
    	        );
    	 when(donorService.getDonorByBloodGroupNameAndAge("O+ve",23)).thenReturn(donors);
    	 mockMvc.perform(post("/user/donor/getByBloodGrouNameAndAge/O+ve/23")
    	        .header("X-User-Role", "ADMIN")
    	        .contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk()) // Expect 200 OK
    			.andExpect(jsonPath("$.length()").value(2)) // Expect 2 donors
    			.andExpect(jsonPath("$[0].donorName").value("Kasiram")) // First donor check
    			.andExpect(jsonPath("$[1].donorName").value("Rajesh")); 
    	 verify(donorService, times(1)).getDonorByBloodGroupNameAndAge("O+ve",23);
    }
    @Test
    void testGetDonorByBloodGroupNameAndAge_NonAdminRole_Forbidden() throws Exception {
        mockMvc.perform(post("/user/donor/getByBloodGrouNameAndAge/O+ve/23")
                        .header("X-User-Role", "USER") // Simulating unauthorized user role
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Expect 403 Forbidden
        verify(donorService,never()).getDonorByBloodGroupNameAndAge("O+ve",23);
    }
    @Test
    void testGetDonorByName_AuthorizedRole_Admin() throws Exception {
    	Donors donor= new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17));
    	when(donorService.getDonorByname("Kasiram")).thenReturn(donor);
    	mockMvc.perform(get("/user/donor/getByName/Kasiram")
                .header("X-User-Role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.donorName").value("Kasiram"))
        .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
        .andExpect(jsonPath("$.age").value(23));
    	 verify(donorService,times(1)).getDonorByname("Kasiram");
    }
    @Test
    void testGetDonorByName_AuthorizedRole_User() throws Exception {
    	Donors donor= new Donors(1L, "Kasiram", 23, "O+ve", "kasiram186@gmail.com", 9952563951L, LocalDate.of(2001, 4, 17));
    	when(donorService.getDonorByname("Kasiram")).thenReturn(donor);
    	mockMvc.perform(get("/user/donor/getByName/Kasiram")
                .header("X-User-Role", "USER")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.donorName").value("Kasiram"))
        .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
        .andExpect(jsonPath("$.age").value(23));
    	 verify(donorService,times(1)).getDonorByname("Kasiram");
    }
    @Test
    void testGetDonorByName_UnauthorizedRole() throws Exception {
        mockMvc.perform(get("/getByName/John")
                        .header("X-User-Role", "GUEST")  // Unauthorized role
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // Verify that the service method is never called when unauthorized
        verify(donorService, never()).getDonorByname(anyString());
    }
}
