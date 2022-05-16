package com.assignment.taxiCom;

import com.assignment.taxiCom.entity.Driver;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaxiComApplicationTests {
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final DateTimeFormatter dateFormatterWithZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SessionFactory sessionFactory;

	@BeforeEach
	public void setup(){
		sessionFactory.getCurrentSession().createNativeQuery("truncate table booking restart identity").executeUpdate();
		sessionFactory.getCurrentSession().createNativeQuery("truncate table invoice restart identity cascade").executeUpdate();
		sessionFactory.getCurrentSession().createNativeQuery("truncate table customer restart identity cascade").executeUpdate();
		sessionFactory.getCurrentSession().createNativeQuery("truncate table driver restart identity cascade").executeUpdate();
		sessionFactory.getCurrentSession().createNativeQuery("truncate table car restart identity cascade").executeUpdate();
	}


//	Positive Tests
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

	@Test
	public void addCarTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"vin\": \"WI12419532\",\n" +
						"    \"make\": \"LADA\",\n" +
						"    \"model\": \"REE\",\n" +
						"    \"color\": \"red\",\n" +
						"    \"convertible\": false,\n" +
						"    \"rating\": 5,\n" +
						"    \"licensePlate\": \"GH124-1251\",\n" +
						"    \"ratePerKilometer\": 10}");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void addDriverTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/drivers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"licenseNumber\": \"GHE123\",\n" +
						"    \"phoneNumber\": \"09153851\",\n" +
						"    \"rating\": 10}");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void assignCarTest() throws Exception {
		addCarTest();
		addDriverTest();

		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/assign")
				.param("car_id", "1")
				.param("driver_id", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// Check if car 1 has been assigned to driver 1
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{'content' : [{'id' : 1, 'car' : {'id' : 1}}]}"));
	}

	@Test
	public void addBookingTest() throws Exception {
		String pickUpTime = ZonedDateTime.now().plusMinutes(20).format(dateFormatterWithZone).toString();
		String dropOffTime = ZonedDateTime.now().plusHours(2).format(dateFormatterWithZone).toString();
		String requestBody = String.format("{\"distance\": 1000,\n" +
				"    \"startingLocation\": \"HCM\",\n" +
				"    \"endLocation\": \"DN\",\n" +
				"    \"pickUpTime\": \"%s\",\n" +
				"    \"dropOffTime\": \"%s\"}", pickUpTime, dropOffTime);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void addInvoiceTest() throws Exception {
		addBookingTest();
		addCustomerTest();
		assignCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());

		RequestBuilder request = MockMvcRequestBuilders
				.post("/invoices")
				.param("bookingId", "1")
				.param("customerId", "1")
				.param("carId", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}




	@Test
	public void getAllBookingTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void updateBookingTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders
				.put("/bookings")
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

	@Test
	public void getBookingByIdTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/id?bookingId=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id':1}"))
				.andReturn();
	}

	@Test
	public void getBookingByInvoiceIdTest() throws Exception {
		assignCarTest();
		addCustomerTest();
		addBookingTest();
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/invoiceId?invoiceId=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id':1}"))
				.andReturn();
	}

	@Test
	public void filterBookingByCreatedTimeTest() throws Exception {
		addBookingTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/bookings/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Order(6)
	@Test
	public void FilterBookingByPickUpTimeTest() throws Exception {
		addBookingTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/bookings/pickUpTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void FilterBookingByDropOffTimeTest() throws Exception {
		addBookingTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/bookings/dropOffTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void FilterBookingByDistance() throws Exception {
		addBookingTest();
		String path = String.format("/bookings/distance/%s/%s", 500, 1500);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void FilterBookingByStartLocation() throws Exception {
		addBookingTest();
		String path = String.format("/bookings/startLocation/%s", "HCM");
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void FilterBookingByEndLocation() throws Exception {
		addBookingTest();
		String path = String.format("/bookings/endLocation/%s", "DN");
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getAllInvoiceTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/invoices");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getInvoiceByIdTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/invoices?id=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id':1}"))
				.andReturn();
	}

	@Test
	public void filterInvoiceByCreatedTimeTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getRevenueOfCustomerByPeriodTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/customerRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
//				.andExpect(content().json("{'':}"))
				.andReturn();
	}

	@Test
	public void getRevenueOfDriverByPeriodTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/driverRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
//				.andExpect(content().json("{'':}"))
				.andReturn();
	}

	@Test
	public void getInvoiceOfCustomerByPeriodTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/customerInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getInvoiceOfDriverByPeriodTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/invoices/driverInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getAllCustomerTest() throws Exception {
		addCustomerTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/customers");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content': [{'id': 1}]}"))
				.andReturn();
	}

	@Test
	public void getCustomerByIdTest() throws Exception {
		addCustomerTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/id?customerId=1");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id': 1}"))
				.andReturn();
	}

	@Test
	public void getCustomerByPhoneTest() throws Exception {
		addCustomerTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/phone?phone=0912345678");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id': 1}"))
				.andReturn();
	}

	@Test
	public void filterCustomerByCreatedTimeTest() throws Exception {
		addCustomerTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone).toString();
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone).toString();

		String path = String.format("/customers/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content': [{'id': 1}]}"))
				.andReturn();
	}

	@Test
	public void getCustomerByNameTest() throws Exception {
		addCustomerTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/name?name=mike");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content': [{'id': 1}]}"))
				.andReturn();
	}

	@Test
	public void getCustomerByAddressTest() throws Exception {
		addCustomerTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/customers/address?address=123 bsb, hsd, uhd");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content': [{'id': 1}]}"))
				.andReturn();
	}

	@Test
	public void updateDriverTest() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
				.andDo(print());
		sessionFactory.getCurrentSession().clear();
		RequestBuilder request = MockMvcRequestBuilders.put("/drivers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\" : 1,\n" +
						"    \"licenseNumber\": \"GHE123\",\n" +
						"    \"phoneNumber\": \"09153851\",\n" +
						"    \"rating\": 5}");
		mockMvc.perform(request)
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
				.andDo(print())
				.andExpect(content().json("{'content' : [{'id':1, 'rating':5}]}"));
	}



	@Test
	public void deleteInvoiceTest() throws Exception {
		addCustomerTest();
//		assignCarTest();
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/invoices?invoiceId=1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void deleteBookingTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/bookings?bookingId=1")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void deleteCustomerTest() throws Exception {
		addCustomerTest();
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
	public void addBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/bookings");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
				.andExpect(result1 -> Assertions.assertTrue(result1.getResolvedException() instanceof HttpMessageNotReadableException))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void addBookingTestWithLackOfParameter() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"distance\": 1000,\n" +
						"    \"startingLocation\": \"HCM\",\n" +
						"    \"pickUpTime\": \"2022-02-01 08:00:00\",\n" +
						"    \"dropOffTime\": \"2022-03-01 08:00:00\"}");
		MvcResult result = mockMvc.perform(request)
				.andDo(print())
//				.andExpect(status().is4xxClientError())
				.andExpect(result1 -> Assertions.assertTrue(result1.getResolvedException() instanceof Exception))
				.andReturn();
	}

	@Test
	public void addBookingTestWithWrongDateTimeFormat() throws Exception {
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