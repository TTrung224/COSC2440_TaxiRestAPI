package com.assignment.taxiCom;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaxiComApplicationTests {

	@Autowired
	private MockMvc mockMvc;

//	Positive Tests
	@Order(1)
	@Test
	public void AddBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"distance\": 1000,\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"endLocation\": \"DN\",\n" +
						"    \"pickUpTime\": \"2022-02-01 08:00:00 ICT\",\n" +
						"    \"dropOffTime\": \"2022-03-01 08:00:00 ICT\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Order(2)
	@Test
	public void GetAllBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/booking");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(3)
	@Test
	public void UpdateBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.put("/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\n" +
						"    \"distance\": \"1000\",\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"endLocation\": \"DN\",\n" +
						"    \"pickUpTime\": \"2022-02-01 08:00:00 ICT\",\n" +
						"    \"dropOffTime\": \"2022-03-01 08:00:00 ICT\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Order(4)
	@Test
	public void DeleteBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	Negative Tests
	@Test
	public void AddBookingTestWithoutRequestBodyTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/booking");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void AddBookingTestWithWrongZonedDateTimeFormat() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"distance\": 1000,\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"endLocation\": \"DN\",\n" +
						"    \"pickUpTime\": \"2022-02-01T08:00:00\",\n" +
						"    \"dropOffTime\": \"2022-03-01T08:00:00\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void UpdateBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.put("/booking");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void DeleteBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/booking");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}