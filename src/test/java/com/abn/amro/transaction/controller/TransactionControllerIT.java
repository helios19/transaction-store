package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.repository.TransactionRepository;
import com.abn.amro.transaction.service.CsvService;
import com.abn.amro.transaction.service.TransactionService;
import com.abn.amro.transaction.service.TransactionSummaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
//@Ignore
//@RunWith(SpringRunner.class)

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = {Application.class})
@ContextConfiguration(name = "contextWithFakeBean")
//@AutoConfigureMockMvc
@Import(TransactionController.class)
@ImportAutoConfiguration(PropertyPlaceholderAutoConfiguration.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerIT {
    @Configuration
    public static class TestContext{
        @Bean
        public static PropertySourcesPlaceholderConfigurer properties(){
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
//	@Autowired
//	MockMvc mockMvc;

	@MockBean
    TransactionService transactionService;

    @MockBean
    TransactionSummaryService transactionSummaryService;

    @MockBean
    CsvService csvService;

    @Autowired
    MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    private TransactionRepository transactionRepository;

//    @Autowired
//    private WebApplicationContext webApplicationContext;


    @Test
    public void testCreateRetrieveWithMockMVC() throws Exception {
//		this.mockMvc.perform(post("/students")).andExpect(status().is2xxSuccessful());
//		this.mockMvc.perform(get("/students/1")).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().string(containsString("Rajesh")));

        mockMvc.perform(get("/transaction-summary/all"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.customerId", is("1")))
                .andExpect(jsonPath("$.month", is("10")))
                .andExpect(jsonPath("$.currentBalance", is(421.27)))
                .andExpect(jsonPath("$.classification", is("UNKNOWN")));

    }


//	@Test
//	public void shouldFindTransactionByCustomerIdAndMonth() throws Exception {
//		mockMvc.perform(get("/transaction-summary/"))
//				.andDo(print())
//				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
//				.andExpect(jsonPath("$.customerId", is("1")))
//				.andExpect(jsonPath("$.month", is("10")))
//				.andExpect(jsonPath("$.currentBalance", is(421.27)))
//				.andExpect(jsonPath("$.classification", is("UNKNOWN")));
//	}
//
//	@Test
//	public void shouldThrowExceptionWhenTransactionIsNotFound() throws Exception {
//		String unknownCustomerId = "1111";
//		String month = "1";
//
//		mockMvc.perform(get("/transaction-summary/" + unknownCustomerId + "/" + month))
//				.andDo(print())
//				.andExpect(status().isNotFound())
//				.andExpect(content().contentType(contentType))
//				.andExpect(jsonPath("$[0].logref", is("error")))
//				.andExpect(jsonPath("$[0].message", is("No transaction found for customer id:" + unknownCustomerId)));
//	}
//
//	protected String json(Object o) throws IOException {
//		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//		new MappingJackson2HttpMessageConverter().write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//		return mockHttpOutputMessage.getBodyAsString();
//	}

}
