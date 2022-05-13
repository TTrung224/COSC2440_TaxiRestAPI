package com.assignment.taxiCom;

import org.apache.tomcat.jni.Local;
import org.junit.After;
import org.junit.Assert;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaxiComApplicationTests {
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final DateTimeFormatter dateFormatterWithZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

	@Autowired
	private MockMvc mockMvc;

//	Positive Tests
	@Order(1)
	@Test
	public void addCustomerTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\": \"123 bsb, hsd, uhd\",\n" +
						"    \"name\": \"Mike\",\n" +
						"    \"phone\": \"0912345678\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Order(1)
	@Test
	public void addBookingTest() throws Exception {
		String pickUpTime = LocalDateTime.now().plusMinutes(20).format(dateFormatter).toString();
		String dropOffTime = LocalDateTime.now().plusHours(2).format(dateFormatter).toString();
		String requestBody = String.format("{\"distance\": 1000,\n" +
				"    \"startingLocation\": \"HCM\",\n" +
				"    \"endLocation\": \"DN\",\n" +
				"    \"pickUpTime\": %s,\n" +
				"    \"dropOffTime\": %s}", pickUpTime, dropOffTime);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Order()
	@Test
	public void addInvoiceTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/invoices?bookingId=&carId=&CustomerId=")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}




	@Order(2)
	@Test
	public void getAllBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(3)
	@Test
	public void updateBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.put("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\n" +
						"    \"distance\": \"1000\",\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"endLocation\": \"DN\",\n" +
						"    \"pickUpTime\": \"2022-02-01 08:00:00\",\n" +
						"    \"dropOffTime\": \"2022-03-01 08:00:00\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Order(4)
	@Test
	public void getBookingByIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/id?bookingId=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

//	@Order(4)
	@Test
	public void getBookingByInvoiceIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/invoiceId?invoiceId=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(5)
	@Test
	public void filterBookingByCreatedTimeTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/bookings/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(6)
	@Test
	public void FilterBookingByPickUpTimeTest() throws Exception {
		String periodStart = LocalDateTime.now().minusHours(6).format(dateFormatter).toString();
		String periodEnd = LocalDateTime.now().plusHours(6).format(dateFormatter).toString();

		String path = String.format("/bookings/pickUpTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(7)
	@Test
	public void FilterBookingByDropOffTimeTest() throws Exception {
		String periodStart = LocalDateTime.now().minusHours(6).format(dateFormatter).toString();
		String periodEnd = LocalDateTime.now().plusHours(6).format(dateFormatter).toString();

		String path = String.format("/bookings/dropOffTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(8)
	@Test
	public void FilterBookingByDistance() throws Exception {
		String path = String.format("/bookings/distance/%s/%s", 500, 1500);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(9)
	@Test
	public void FilterBookingByStartLocation() throws Exception {
		String path = String.format("/bookings/startLocation/%s", "HCM");
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	@Order(10)
	@Test
	public void FilterBookingByEndLocation() throws Exception {
		String path = String.format("/bookings/endLocation/%s", "DN");
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

//	@Order()
	@Test
	public void getAllInvoiceTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/invoices");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getInvoiceByIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/invoices?id=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void filterInvoiceByCreatedTimeTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getRevenueOfCustomerByPeriodTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/customerRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getRevenueOfDriverByPeriodTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/driverRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getInvoiceOfCustomerByPeriodTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/customerInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getInvoiceOfDriverByPeriodTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/driverInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getAllCustomerTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/customers");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getCustomerByIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/customers?id=3");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getCustomerByPhoneTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/phone?phone=0912345678");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void filterCustomerByCreatedTimeTest() throws Exception {
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/customers/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getCustomerByNameTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/name?name=mike");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}

	//	@Order()
	@Test
	public void getCustomerByAddressTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/address?address=123 bsb, hsd, uhd");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();
	}




	//	@Order(3)
	@Test
	public void deleteInvoiceTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/invoices?invoiceId=1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	//	@Order(3)
	@Test
	public void deleteBookingTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/bookings?bookingId=1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	//	@Order(3)
	@Test
	public void deleteCustomerTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/customers?customerId=1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}


//	Negative Tests
	@Test
	public void addBookingTestWithoutRequestBodyTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
//				.andExpect(result1 -> Assert.assertTrue(result1.getResolvedException() instanceof HttpMessageNotReadableException))
				.andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	public void addBookingTestWithWrongZonedDateTimeFormat() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/bookings")
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
	public void filterBookingByPickUpTimeWithoutPathVariableTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/pickUpTime");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void UpdateBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.put("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void DeleteBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}