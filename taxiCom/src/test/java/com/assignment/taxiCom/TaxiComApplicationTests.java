package com.assignment.taxiCom;

import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiComApplicationTests {

	@Autowired
	private MockMvc mockMvc;

//	Positive Tests
	@Test
	public void AddBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"distance\": 1000,\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"endLocation\": \"DN\",\n" +
						"    \"pickUpTime\": \"2022-02-01T08:00:00+07:00\",\n" +
						"    \"dropOffTime\": \"2022-03-01T08:00:00+07:00\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void GetAllBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/booking");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

//	Negative Tests
	@Test
	public void AddBookingWithoutBodyTest() throws Exception {
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


}