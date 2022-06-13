package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.repository.TransactionRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.abn.amro.common.utils.ClassUtils.COUNTERS_COLLECTION_NAME;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest({"server.port=0"})
//@WebMvcTest(controllers = TransactionController.class)
@Ignore
public class TransactionControllerIT {

//	@Autowired
//	MockMvc mockMvc;
//
//	@MockBean
//	StudentService service;



	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MongodExecutable mongodExec;

//	@Autowired
//	private MongoClient mongoClient;

	private static MongodExecutable staticMongodExec;
//	private static MongoClient staticMongoClient;

    @Before
    public void setUp() throws Exception {

		mockMvc = webAppContextSetup(webApplicationContext).build();

		// init mongodb
		staticMongodExec = mongodExec;
//		staticMongoClient = mongoClient;
	}

    @After
    public void tearDown() throws Exception {
		// reset transaction collection
		transactionRepository.getMongoTemplate().dropCollection(Transaction.class);
		// reset sequence collection
		transactionRepository.getMongoTemplate().dropCollection(COUNTERS_COLLECTION_NAME);
	}

	@AfterClass
	public static void postContruct() {
		// stop mongodb
//		staticMongoClient.close();
		staticMongodExec.stop();
	}


	@Test
	public void shouldFindTransactionByCustomerIdAndMonth() throws Exception {
		mockMvc.perform(get("/transaction-summary/1/10"))
				.andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.customerId", is("1")))
				.andExpect(jsonPath("$.month", is("10")))
				.andExpect(jsonPath("$.currentBalance", is(421.27)))
				.andExpect(jsonPath("$.classification", is("UNKNOWN")));
	}

	@Test
	public void shouldThrowExceptionWhenTransactionIsNotFound() throws Exception {
		String unknownCustomerId = "1111";
		String month = "1";

		mockMvc.perform(get("/transaction-summary/" + unknownCustomerId + "/" + month))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$[0].logref", is("error")))
				.andExpect(jsonPath("$[0].message", is("No transaction found for customer id:" + unknownCustomerId)));
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		new MappingJackson2HttpMessageConverter().write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
