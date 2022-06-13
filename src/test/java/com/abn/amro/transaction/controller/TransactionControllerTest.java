package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.service.TransactionService;
import com.abn.amro.transaction.service.TransactionServiceTest;
import com.abn.amro.transaction.service.TransactionSummaryService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.abn.amro.common.utils.ClassUtils.HUNDRED;
import static com.abn.amro.common.utils.ClassUtils.TEN_MILLIONS;
import static com.abn.amro.common.utils.ClassUtils.toDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebMvcTest//(controllers = TransactionController.class)

//@SpringBootTest
//@AutoConfigureMockMvc

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration =
        {TransactionServiceTest.TestAppConfig.class, GroovyTemplateAutoConfiguration.class})
@Import({TransactionControllerTest.TestAppConfig.class})
public class TransactionControllerTest {

//    @Mock
//    private HelloRepository helloRepository;
//
//    @InjectMocks // auto inject helloRepository
//    private HelloService helloService = new HelloServiceImpl();
//
//    @BeforeEach
//    void setMockOutput() {
//        when(helloRepository.get()).thenReturn("Hello Mockito From Repository");
//    }
//
//    @DisplayName("Test Mock helloService + helloRepository")
//    @Test
//    void testGet() {
//        assertEquals("Hello Mockito From Repository", helloService.get());
//    }







    @MockBean
    private TransactionService transactionService;

//    @MockBean
//    private TransactionRepository transactionRepository;

    @SpyBean
    private TransactionSummaryService transactionSummaryService;

    @Autowired
    private MockMvc mockMvc;

    private Transaction transaction = Transaction
            .builder()
            .recordCode("315")
            .clientType("CL  ")
            .clientNum("4321")
            .acctNum("0002")
            .subAcctNum("0001")
            .oppositePartyCode("SGXDC ")
            .productGroupCode("FU")
            .exchangeCode("SGX ")
            .symbol("NK    ")
            .expirationDate("20100910")
            .currencyCode("JPY")
            .movementCode("01")
            .buySellCode("B")
            .qttyLongSign(" ")
            .qttyLong(new BigInteger("0000000001"))
            .qttyShortSign(" ")
            .qttyShort(new BigInteger("0000000000"))
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(TEN_MILLIONS))
            .traderInitials("      ")
            .oppositeTraderId("       ")
            .openCloseCode(" ")
            .filler("O                                                                                                                              ")
            .build();
//            .customer("1")
//            .date(toDate("1/10/2016 2:51:23 AM"))
//            .amount(BigDecimal.valueOf(23.4))
//            .description("first transaction description")
//            .build();


    @Test
    public void shouldReturnAllTransactionSummaries() throws Exception {

        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList(transaction));

//        when(transactionSummaryService.buildTransactionSummaryReport(any(List.class)))
//                .thenReturn(Lists.newArrayList(ClassificationEnum.MORNING_PERSON));
//
//        given().
//                when().
//                get("/transaction-summary/").
//                then().
//                statusCode(HttpServletResponse.SC_OK).
//                contentType(ContentType.JSON).
//                body("customerId", equalTo("1")).
//                body("month", equalTo("10")).
//                body("currentBalance", is((float) 23.4)).
//                body("classification[0]", equalTo(ClassificationEnum.MORNING_PERSON.name())).
//                body("transactions", notNullValue()).
//                log().all(true);


        mockMvc.perform(get("/transaction-summary/"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
//                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
//                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
                .andDo(print())
                .andDo(log());


        verify(transactionService, times(1)).findAll();
        verifyNoMoreInteractions(transactionService);
        verify(transactionSummaryService, times(1)).buildTransactionSummaryReport(any(List.class));
        verifyNoMoreInteractions(transactionSummaryService);
    }














//    @InjectMocks
//    TransactionController controller;
//
//    @Mock
//    private TransactionService transactionService;
//
//    @Mock
//    private ClassificationService classificationService;
//
//    private MockMvc mvc;
//
//    private Transaction transaction = Transaction
//            .builder()
//            .customer("1")
//            .date(toDate("1/10/2016 2:51:23 AM"))
//            .amount(BigDecimal.valueOf(23.4))
//            .description("first transaction description")
//            .build();
//
//    @Before
//    public void setUp() throws Exception {
//        initMocks(this);
//        mvc = MockMvcBuilders.standaloneSetup(controller)
//                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
//        RestAssuredMockMvc.mockMvc(mvc);
//    }
//
//    @Test
//    public void shouldFindByCustomerIdAndMonth() throws IOException {
//
//        when(transactionService.findByCustomerId(any(String.class)))
//                .thenReturn(Lists.newArrayList(transaction));
//
//        when(classificationService.classifyCustomer(any(List.class)))
//                .thenReturn(Lists.newArrayList(ClassificationEnum.MORNING_PERSON));
//
//        given().
//                when().
//                get("/transaction-summary/1/10").
//                then().
//                statusCode(HttpServletResponse.SC_OK).
//                contentType(ContentType.JSON).
//                body("customerId", equalTo("1")).
//                body("month", equalTo("10")).
//                body("currentBalance", is((float) 23.4)).
//                body("classification[0]", equalTo(ClassificationEnum.MORNING_PERSON.name())).
//                body("transactions", notNullValue()).
//                log().all(true);
//
//        verify(transactionService, times(1)).findByCustomerId(any(String.class));
//        verifyNoMoreInteractions(transactionService);
//        verify(classificationService, times(1)).classifyCustomer(any(List.class));
//        verifyNoMoreInteractions(classificationService);
//    }


//    @Configuration
//    @Import({ CustomerConfig.class, SchedulerConfig.class })
//    public class AppConfig {
//
//    }

    @Configuration
//    @EnableAutoConfiguration
//    @EnableMongoRepositories(basePackages = "com.abn.amro.transaction.repository")
    @ComponentScan({
            "com.abn.amro.transaction.service"
    })
    public static class TestAppConfig {

//        @Bean
//        public TransactionRepository transactionRepository() {
//            return null;
//        }
    }
}
