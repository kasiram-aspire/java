package com.Blood.Inventory_Service.ControllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.Blood.Inventory_Service.Controller.InventoryController;
import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;
import com.Blood.Inventory_Service.Service.InventoryService;
import com.Blood.Inventory_Service.dto.Donordto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest
@AutoConfigureMockMvc
public class InventroyControllerTest {
	private MockMvc mockMvc;
	   @Mock
	   InventoryService inventoryservice;
	   @Mock
	   Donordto donordto;
	   @InjectMocks
	   InventoryController  inventorycontroller;
	   private ObjectMapper objectMapper = new ObjectMapper(); 
	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(inventorycontroller).build();
	        objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	    }
	    @Test
	    public void testAddDonartoTheInventory_AdminRole_Success() throws Exception {
	    	 String donorName = "kasiram";
	         Long unitOfBlood = 2L;
	         LocalDate bloodGivenDate = LocalDate.of(2024, 3, 5);
	        // Given
	        Inventory mockInventory = new Inventory(1L, "kasiram","O+ve",23,2L,LocalDate.of(2024, 3, 5));

	        when(inventoryservice.addDonorToTheInventory(donorName, unitOfBlood, bloodGivenDate)).thenReturn(mockInventory);

	        // When & Then
	        mockMvc.perform(post("/inventroy/addDonorInventory")
	                .header("X-User-Role","ADMIN")
	                .param("donorname", donorName)
	                .param("UnitOfBlood", String.valueOf(unitOfBlood))
	                .param("BloodGivenDate", bloodGivenDate.toString())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1)) 
                    .andExpect(jsonPath("$.donorname").value("kasiram")) 
                    .andExpect(jsonPath("$.bloodGroup").value("O+ve")) // Check blood group
                    .andExpect(jsonPath("$.age").value(23)) // Check age
                    .andExpect(jsonPath("$.unitOfBlood").value(2L)) // Check blood units
                    .andExpect(jsonPath("$.bloodGivenDate").value("2024-03-05")); // date
	    }
	    @Test
	    public void testAddDonartoTheInventory_NonAdminRole_Forbidden() throws Exception {
	    	String donorName = "kasiram";
	         Long unitOfBlood = 2L;
	         LocalDate bloodGivenDate = LocalDate.of(2024, 3, 5);
	        // Given
	        Inventory mockInventory = new Inventory(1L, "kasiram","O+ve",23,2L,LocalDate.of(2024, 3, 5));

	        when(inventoryservice.addDonorToTheInventory(donorName, unitOfBlood, bloodGivenDate)).thenReturn(mockInventory);
	        mockMvc.perform(post("/inventroy/addDonorInventory")
	                .header("X-User-Role","USER")
	                .param("donorname", donorName)
	                .param("UnitOfBlood", String.valueOf(unitOfBlood))
	                .param("BloodGivenDate", bloodGivenDate.toString())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden());
	        verify(inventoryservice,never()).addDonorToTheInventory(donorName, unitOfBlood, bloodGivenDate);
	    }
	    @Test
	    public void testGetDonorFromInventory_AdminRole_Success() throws Exception {
	        // Given
	        List<Inventory> mockDonors = Arrays.asList(
	            new Inventory(1L, "Kasiram", "O+ve", 23, 2L, LocalDate.of(2024, 3, 5)),
	            new Inventory(2L, "Arun", "B+ve", 30, 1L, LocalDate.of(2024, 2, 10))
	        );

	        when(inventoryservice.getDonorFromInventroy()).thenReturn(mockDonors);
	        mockMvc.perform(get("/inventroy/getDonor")
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.size()").value(2)) // Expect 2 donors
	                .andExpect(jsonPath("$[0].id").value(1)) 
	                .andExpect(jsonPath("$[0].donorname").value("Kasiram"))
	                .andExpect(jsonPath("$[0].bloodGroup").value("O+ve"))
	                .andExpect(jsonPath("$[0].age").value(23))
	                .andExpect(jsonPath("$[0].unitOfBlood").value(2))
	                .andExpect(jsonPath("$[0].bloodGivenDate").value("2024-03-05"))
	                .andExpect(jsonPath("$[1].id").value(2)) 
	                .andExpect(jsonPath("$[1].donorname").value("Arun"))
	                .andExpect(jsonPath("$[1].bloodGroup").value("B+ve"))
	                .andExpect(jsonPath("$[1].age").value(30))
	                .andExpect(jsonPath("$[1].unitOfBlood").value(1))
	                .andExpect(jsonPath("$[1].bloodGivenDate").value("2024-02-10"));
	    }
	    @Test
	    public void testGetDonorFromInventory_UserRole_Success() throws Exception {
	    	List<Inventory> mockDonors = Arrays.asList(
		            new Inventory(1L, "Kasiram", "O+ve", 23, 2L, LocalDate.of(2024, 3, 5)),
		            new Inventory(2L, "Arun", "B+ve", 30, 1L, LocalDate.of(2024, 2, 10))
		        );

		        when(inventoryservice.getDonorFromInventroy()).thenReturn(mockDonors);
		        mockMvc.perform(get("/inventroy/getDonor")
		                .header("X-User-Role", "USER")
		                .contentType(MediaType.APPLICATION_JSON))
		                .andExpect(status().isOk()) // 200 OK
		                .andExpect(jsonPath("$.size()").value(2)) // Expect 2 donors
		                .andExpect(jsonPath("$[0].id").value(1)) 
		                .andExpect(jsonPath("$[0].donorname").value("Kasiram"))
		                .andExpect(jsonPath("$[0].bloodGroup").value("O+ve"))
		                .andExpect(jsonPath("$[0].age").value(23))
		                .andExpect(jsonPath("$[0].unitOfBlood").value(2))
		                .andExpect(jsonPath("$[0].bloodGivenDate").value("2024-03-05"))
		                .andExpect(jsonPath("$[1].id").value(2)) 
		                .andExpect(jsonPath("$[1].donorname").value("Arun"))
		                .andExpect(jsonPath("$[1].bloodGroup").value("B+ve"))
		                .andExpect(jsonPath("$[1].age").value(30))
		                .andExpect(jsonPath("$[1].unitOfBlood").value(1))
		                .andExpect(jsonPath("$[1].bloodGivenDate").value("2024-02-10"));
	    }
	    @Test
	    public void testGetDonorFromInventory_UnauthorizedRole_Forbidden() throws Exception {
	        mockMvc.perform(get("/inventroy/getDonor")
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // 403 Forbidden
	    }
	    @Test
	    public void testGetTotalAmountOfBlood_AdminRole_Success() throws Exception {
	       
	        List<BloodCount> mockBloodCounts = Arrays.asList(
	            new BloodCount(1L,"O+ve", 10L),
	            new BloodCount(2L,"A+ve", 5L)
	        );

	        when(inventoryservice.getTotalAmountOfBlood()).thenReturn(mockBloodCounts);
	        mockMvc.perform(get("/inventroy/getBloodCount")
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.size()").value(2)) // Expect 2 blood groups
	                .andExpect(jsonPath("$[0].bloodGroup").value("O+ve"))
	                .andExpect(jsonPath("$[0].units").value(10))
	                .andExpect(jsonPath("$[1].bloodGroup").value("A+ve"))
	                .andExpect(jsonPath("$[1].units").value(5));
	    }
	    @Test
	    public void testGetTotalAmountOfBlood_UserRole_Success() throws Exception {
	    	 List<BloodCount> mockBloodCounts = Arrays.asList(
	 	            new BloodCount(1L,"O+ve", 10L),
	 	            new BloodCount(2L,"A+ve", 5L)
	 	        );

	 	        when(inventoryservice.getTotalAmountOfBlood()).thenReturn(mockBloodCounts);
	 	        mockMvc.perform(get("/inventroy/getBloodCount")
	 	                .header("X-User-Role", "USER")
	 	                .contentType(MediaType.APPLICATION_JSON))
	 	                .andExpect(status().isOk()) // 200 OK
	 	                .andExpect(jsonPath("$.size()").value(2)) // Expect 2 blood groups
	 	                .andExpect(jsonPath("$[0].bloodGroup").value("O+ve"))
	 	                .andExpect(jsonPath("$[0].units").value(10))
	 	                .andExpect(jsonPath("$[1].bloodGroup").value("A+ve"))
	 	                .andExpect(jsonPath("$[1].units").value(5));
	    }
	    @Test
	    public void testGetTotalAmountOfBlood_UnauthorizedRole_Forbidden() throws Exception {
	        mockMvc.perform(get("/inventroy/getBloodCount")
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // 403 Forbidden
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_AdminRole_Success() throws Exception {
	        // Given
	        String bloodGroup = "O+ve";
	        BloodCount mockBloodCount = new BloodCount(1L,bloodGroup, 10L);

	        when(inventoryservice.getCountBasedOnBloodGroup(bloodGroup)).thenReturn(mockBloodCount);

	        // When & Then
	        mockMvc.perform(get("/inventroy/getBloodCount/{bloodgroup}", bloodGroup)
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
	                .andExpect(jsonPath("$.units").value(10));
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_UserRole_Success() throws Exception {
	        // Given
	        String bloodGroup = "A+ve";
	        BloodCount mockBloodCount = new BloodCount(1L,bloodGroup, 5L);

	        when(inventoryservice.getCountBasedOnBloodGroup(bloodGroup)).thenReturn(mockBloodCount);
	        mockMvc.perform(get("/inventroy/getBloodCount/{bloodgroup}", bloodGroup)
	                .header("X-User-Role", "USER")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.bloodGroup").value("A+ve"))
	                .andExpect(jsonPath("$.units").value(5));
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_UnauthorizedRole_Forbidden() throws Exception {
	        String bloodGroup = "B+ve";
	        mockMvc.perform(get("/inventroy/getBloodCount/{bloodgroup}", bloodGroup)
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // 403 Forbidden
	    }
	    @Test
	    public void testGetCountBasedOnBloodGroup_NotFound() throws Exception {
	        String bloodGroup = "AB-ve";

	        when(inventoryservice.getCountBasedOnBloodGroup(bloodGroup)).thenReturn(null);
	        mockMvc.perform(get("/inventroy/getBloodCount/{bloodgroup}", bloodGroup)
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // Ensure 200 OK, but the response may be null
	                .andExpect(jsonPath("$").doesNotExist()); // Expecting null response
	    }
	    @Test
	    public void testDeleteUserFromInventory_AdminRole_Success() throws Exception {
	        String donorName = "Kasiram";
	        when(inventoryservice.deleteUserFromInventory(donorName)).thenReturn(true);
	        mockMvc.perform(post("/inventroy/deleteUserFromInventory/{donorname}",donorName)
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(content().string("Inventory with name 'Kasiram' deleted successfully."));
	    }
	    @Test
	    public void testDeleteUserFromInventory_UserRole_Success() throws Exception {
	        String donorName = "Kasiram";
	        when(inventoryservice.deleteUserFromInventory(donorName)).thenReturn(true);
	        mockMvc.perform(post("/inventroy/deleteUserFromInventory/{donorname}",donorName)
	                .header("X-User-Role", "USER")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(content().string("Inventory with name 'Kasiram' deleted successfully."));
	    }
	    @Test
	    public void testDeleteUserFromInventory_UnauthorizedRole_Forbidden() throws Exception {
	        String donorName = "RandomUser";
	        mockMvc.perform(post("/inventroy/deleteUserFromInventory/{donorname}", donorName)
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isForbidden()); // 403 Forbidden
	    }
	    @Test
	    public void testDeleteUserFromInventory_DonorNotFound() throws Exception {
	        String donorName = "UnknownUser";
	        when(inventoryservice.deleteUserFromInventory(donorName)).thenReturn(false);
	        mockMvc.perform(post("/inventroy/deleteUserFromInventory/{donorname}", donorName)
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound()) // 404 Not Found
	                .andExpect(content().string("Inventory with name 'UnknownUser' not found."));
	    }
	    @Test
	    public void testUpdateInventory_AdminRole_Success() throws Exception {
	    	
	        Inventory inventory = new Inventory(1L, "JohnDoe", "O+ve", 30, 3L, LocalDate.of(2024, 3, 5));
	        when(inventoryservice.updateInventroy(any(Inventory.class))).thenReturn(inventory);
	        mockMvc.perform(post("/inventroy/updateInventroy")
	                .header("X-User-Role", "ADMIN")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(inventory)))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.id").value(1))
	                .andExpect(jsonPath("$.donorname").value("JohnDoe"))
	                .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
	                .andExpect(jsonPath("$.age").value(30))
	                .andExpect(jsonPath("$.unitOfBlood").value(3))
	                .andExpect(jsonPath("$.bloodGivenDate").value("2024-03-05"));
	    }
	    @Test
	    public void testUpdateInventory_UserRole_Success() throws Exception {
	    	
	        Inventory inventory = new Inventory(1L, "JohnDoe", "O+ve", 30, 3L, LocalDate.of(2024, 3, 5));
	        when(inventoryservice.updateInventroy(any(Inventory.class))).thenReturn(inventory);
	        mockMvc.perform(post("/inventroy/updateInventroy")
	                .header("X-User-Role", "USER")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(inventory)))
	                .andExpect(status().isOk()) // 200 OK
	                .andExpect(jsonPath("$.id").value(1))
	                .andExpect(jsonPath("$.donorname").value("JohnDoe"))
	                .andExpect(jsonPath("$.bloodGroup").value("O+ve"))
	                .andExpect(jsonPath("$.age").value(30))
	                .andExpect(jsonPath("$.unitOfBlood").value(3))
	                .andExpect(jsonPath("$.bloodGivenDate").value("2024-03-05"));
	    }
	    @Test
	    public void testUpdateInventory_UnauthorizedRole_Forbidden() throws Exception {	
	        Inventory inventory = new Inventory(3L, "RandomUser", "B+ve", 40, 1L, LocalDate.of(2024, 1, 10));
	        mockMvc.perform(post("/inventroy/updateInventroy")
	                .header("X-User-Role", "GUEST") // Unauthorized role
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(inventory)))
	                .andExpect(status().isForbidden()); // 403 Forbidden
	    }







}
