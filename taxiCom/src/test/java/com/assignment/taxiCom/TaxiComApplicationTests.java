package com.assignment.taxiCom;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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
		mockMvc.perform(request)
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
	public void unassignCarTest() throws Exception {
		assignCarTest();

		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/unassign")
				.param("driver_id", "1"))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
				.andExpect(content().json("{'content' : [{'id':1, 'car':null}]}"));
	}

	@Test
	public void addBookingTest() throws Exception {
		String pickUpTime = ZonedDateTime.now().plusMinutes(20).format(dateFormatterWithZone);
		String dropOffTime = ZonedDateTime.now().plusHours(2).format(dateFormatterWithZone);
		String requestBody = String.format("{\"distance\": 1000,\n" +
				"    \"startingLocation\": \"HCM\",\n" +
				"    \"endLocation\": \"DN\",\n" +
				"    \"pickUpTime\": \"%s\",\n" +
				"    \"dropOffTime\": \"%s\"}", pickUpTime, dropOffTime);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}




	@Test
	public void getAllBookingTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings");
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void getBookingByIdTest() throws Exception {
		addBookingTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/id?bookingId=1");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id':1}"))
				.andReturn();
	}

	@Test
	public void getBookingByInvoiceIdTest() throws Exception {
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/invoiceId?invoiceId=1");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id':1, 'invoice':{'id':1}}"))
				.andReturn();
	}

	@Test
	public void filterBookingByCreatedTimeTest() throws Exception {
		addBookingTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/bookings/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void FilterBookingByPickUpTimeTest() throws Exception {
		addBookingTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/bookings/pickUpTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getAllInvoiceTest() throws Exception {
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders.get("/invoices");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}


	@Test
	public void filterInvoiceByCreatedTimeTest() throws Exception {
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getRevenueOfCustomerByPeriodTest() throws Exception {
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/customerRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().string("10000.0"))
				.andReturn();
	}

	@Test
	public void getRevenueOfDriverByPeriodTest() throws Exception {
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/driverRevenue/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().string("10000.0"))
				.andReturn();
	}

	@Test
	public void getInvoiceOfCustomerByPeriodTest() throws Exception {
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/customerInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id':1}]}"))
				.andReturn();
	}

	@Test
	public void getInvoiceOfDriverByPeriodTest() throws Exception {
		addInvoiceTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/invoices/driverInvoice/1/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id': 1}"))
				.andReturn();
	}

	@Test
	public void filterCustomerByCreatedTimeTest() throws Exception {
		addCustomerTest();
		String periodStart = ZonedDateTime.now().minusHours(6).format(dateFormatterWithZone);
		String periodEnd = ZonedDateTime.now().plusHours(6).format(dateFormatterWithZone);

		String path = String.format("/customers/createdTime/%s/%s", periodStart, periodEnd);
		RequestBuilder request = MockMvcRequestBuilders.get(path);
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content': [{'id': 1}]}"))
				.andReturn();
	}

	@Test
	public void getDriverByLicenseTest() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers/license")
						.param("value", "GHE123"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id': 1, 'licenseNumber' : 'GHE123'}"))
				.andReturn();
	}

	@Test
	public void getDriverByRatingTest() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers/rating")
						.param("value", "10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'content':[{'id': 1, 'rating' : 10}]}"))
				.andReturn();
	}

	@Test
	public void sortDriverByRatingTest() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders
						.post("/drivers")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"licenseNumber\": \"GHFG23\",\n" +
								"    \"phoneNumber\": \"09175851\",\n" +
								"    \"rating\": 5}"));
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers/rating/sort"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'pageable': {'sort' : {'sorted' : true}}}"))
				.andReturn();
	}

	@Test
	public void getCarByMakeTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/make")
				.param("value", "Lada"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'make':'LADA'}]}"));
	}

	@Test
	public void getCarByModelTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/model")
						.param("value", "REE"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'model':'REE'}]}"));
	}

	@Test
	public void getCarByColorTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/color")
						.param("value", "red"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'color':'red'}]}"));
	}

	@Test
	public void getCarByRatingTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/rating")
						.param("value", "5"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'rating':5}]}"));
	}

	@Test
	public void getCarByRateTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/rate")
						.param("value", "10"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'ratePerKilometer':10}]}"));
	}

	@Test
	public void getCarByConvertibleTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/convertible")
						.param("value", "false"))
				.andExpect(status().isOk())
				.andExpect(content().json("{content : [{'id':1,'convertible':false}]}"));
	}

	@Test
	public void getCarByLicenseTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/license")
						.param("value", "GH124-1251"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':1,'licensePlate':'GH124-1251'}"));
	}

	@Test
	public void getCarByVinTest() throws Exception {
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/vin")
						.param("value", "WI12419532"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':1,'vin':'WI12419532'}"));
	}

	@Test
	public void getDriverByPhoneTest() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers/phone")
						.param("value", "09153851"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json("{'id': 1, 'phoneNumber' : '09153851'}"))
				.andReturn();
	}

	@Test
	public void getUnassignedCarTest() throws Exception {
		assignCarTest(); // Create a car and assigned it to a driver
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/available"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'totalElements':0}"));
		mockMvc.perform(MockMvcRequestBuilders // Post a new car with no driver
						.post("/cars")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"vin\": \"CL1295-RWN\",\n" +
								"    \"make\": \"Toyota\",\n" +
								"    \"model\": \"Vios\",\n" +
								"    \"color\": \"black\",\n" +
								"    \"convertible\": false,\n" +
								"    \"rating\": 5,\n" +
								"    \"licensePlate\": \"KH152-8693\",\n" +
								"    \"ratePerKilometer\": 10}"));
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/available"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'content' : [{'id':2, 'vin':'CL1295-RWN'}], 'totalElements':1}"));
	}

	@Test
	public void getCarUsageTest() throws Exception {
		addInvoiceTest();
		sessionFactory.getCurrentSession().flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/usage")
						.param("month", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM")))
						.param("year", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))))
				.andDo(print())
				.andExpect(content().json("{'content' : [{'car_license':'GH124-1251','day_used':1}]}"));
	}

	@Test
	public void getAvailableForBookingTest() throws Exception {
		addInvoiceTest(); // Create a car that is not available
		// Create a new car that is available
		mockMvc.perform(MockMvcRequestBuilders
				.post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"vin\": \"CL1295-RWN\",\n" +
						"    \"make\": \"Toyota\",\n" +
						"    \"model\": \"Vios\",\n" +
						"    \"color\": \"black\",\n" +
						"    \"convertible\": false,\n" +
						"    \"rating\": 5,\n" +
						"    \"licensePlate\": \"KH152-8693\",\n" +
						"    \"ratePerKilometer\": 10}"));
		mockMvc.perform(MockMvcRequestBuilders
				.post("/drivers")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"licenseNumber\": \"KH432\",\n" +
						"    \"phoneNumber\": \"091246568\",\n" +
						"    \"rating\": 10}"));
		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/assign")
						.param("car_id", "2")
						.param("driver_id", "2"));
		sessionFactory.getCurrentSession().flush();
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/availableBooking")
				.param("pickUp", ZonedDateTime.now().plusMinutes(20).format(dateFormatterWithZone)))
				.andExpect(content().json("{'content' : [{'id':2, 'vin' : 'CL1295-RWN'}]}"));
	}

	@Test
	public void updateDriverTest() throws Exception {
		addDriverTest();
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
				.andExpect(content().json("{'content' : [{'id':1, 'rating':5}]}"));
	}

	@Test
	public void updateCarTest() throws Exception {
		addCarTest();
		sessionFactory.getCurrentSession().clear();
		RequestBuilder request = MockMvcRequestBuilders.put("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\n" +
						"	 \"vin\": \"WI12419532\",\n" +
						"    \"make\": \"LADA\",\n" +
						"    \"model\": \"REE\",\n" +
						"    \"color\": \"red\",\n" +
						"    \"convertible\": false,\n" +
						"    \"rating\": 10,\n" + // Change rating from 5 to 10
						"    \"licensePlate\": \"GH124-1251\",\n" +
						"    \"ratePerKilometer\": 10}");
		mockMvc.perform(request)
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
				.andExpect(content().json("{'content' : [{'id':1, 'rating':10}]}"));
	}



	@Test
	public void deleteInvoiceTest() throws Exception {
		addInvoiceTest();
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/invoices?invoiceId=1")
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request)
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
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void deleteDriverTest() throws Exception{
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.delete("/drivers")
				.param("driverId", "1"))
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
				.andExpect(content().json("{'totalElements' : 0}"));
	}

	@Test
	public void deleteCarTest() throws Exception{
		addCarTest();
		mockMvc.perform(MockMvcRequestBuilders.delete("/cars")
				.param("carId", "1"))
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
				.andExpect(content().json("{'totalElements' : 0}"));
	}


//	Negative Tests
	@Test
	public void assignDriverToAssignedCar() throws Exception {
		assignCarTest();
		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/assign")
				.param("driver_id","1")
				.param("car_id","1"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void unassignDriverWithNoCar() throws Exception {
		addDriverTest();
		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/unassign")
				.param("driverId", "1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void assignNonExistingDriver() throws Exception {
		assignCarTest();
		mockMvc.perform(MockMvcRequestBuilders.put("/drivers/assign")
				.param("driver_id","2")
				.param("car_id","1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void addBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/bookings");
		mockMvc.perform(request)
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
		mockMvc.perform(request)
				.andDo(print())
//				.andExpect(status().is4xxClientError())
				.andExpect(result1 -> Assertions.assertNotNull(result1.getResolvedException()))
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
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void invalidPickUpTimeToGetAvailableCarBooking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cars/availableBooking")
				.param("pickUp", ZonedDateTime.now().format(dateFormatterWithZone)))
				.andExpect(status().isForbidden());
	}

	@Test
	public void filterBookingByPickUpTimeWithoutPathVariableTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bookings/pickUpTime/");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	public void updateBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.put("/bookings");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void deleteBookingTestWithoutRequestBody() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/bookings");
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}