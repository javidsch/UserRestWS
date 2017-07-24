package com.javidsh.userws;

import com.javidsh.userws.dto.SexType;
import com.javidsh.userws.dto.UserDto;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Properties;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * UserRestControllerTests.java
 * Purpose: Integration test of the UserRestController class.
 * Naming Convention: method[ResourceName]_When[StateUnderTest]_Then[ExpectedBehavior]
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 * 
 * 
 * There are 2(two) important files for integration-testing in /test/resource/:
 * -beforeTestRun.sql (Initial state of database for every test)
 * -expectedValues.properties (Expected JSON data for some tests)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserWSApplication.class}, properties = {"spring.config.name:application-integration-test"})
@WebAppConfiguration
@Sql(scripts = "classpath:beforeTestRun.sql")
public class UserRestControllerTests {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    protected String objectToJSON(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected UserDto JSONToUserDto(String json) throws IOException {
        MockHttpInputMessage mockHttpInputMessage = new MockHttpInputMessage(json.getBytes());
        UserDto userDto = (UserDto) mappingJackson2HttpMessageConverter.read(UserDto.class, mockHttpInputMessage);
        return userDto;
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static Properties expectedValues;

    private static final String URI = "/userws/user";
    private static final String HTTP_URL = "http://localhost"+ URI;
    private final UserDto testUserDto = new UserDto();

    @Value("${usersPerPage}")
    private Integer usersPerPage;

    @BeforeClass
    public static void onceExecutedBeforeAll() throws Exception {
        Resource resource = new ClassPathResource("expectedValues.properties");
        Reader fileReader = new FileReader(resource.getFile());
        expectedValues = new Properties();
        expectedValues.load(fileReader);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        
        testUserDto.setUserName("mark65");
        testUserDto.setFirstName("Mark");
        testUserDto.setLastName("Pellegrino");
        testUserDto.setSex(SexType.MALE);
        testUserDto.setBirthDate(LocalDate.of(1965, Month.MARCH, 9));
        testUserDto.setSingle(false);
        testUserDto.setSalary(BigDecimal.valueOf(9000.20));
        testUserDto.setEmail("mark65@abc.com");
        testUserDto.setFamilyMembersCount(3);
    }

    @Test
    public void usersPerPage_ShouldBe_4_For_Test() throws Exception {
        //usersPerPage=4   --->     in /test/resources/application-integration-test.properties
        assertThat(usersPerPage, is(4));
    }

    //Cases: Get(GET) user by path-variable 'id'
    @Test
    public void getUser_WhenId1_ThenOKWithExpectedJSON() throws Exception {
        String expected = expectedValues.getProperty("getUser_WhenId1_ThenOKWithExpectedJSON");
        mockMvc.perform(get(URI + "/1")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(expected));
    }

    @Test
    public void getUser_WhenId5_ThenOKWithExpectedJSON() throws Exception {
        String expected = expectedValues.getProperty("getUser_WhenId5_ThenOKWithExpectedJSON");
        mockMvc.perform(get(URI + "/5")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(expected));
    }

    @Test
    public void getUser_WhenNotExistId11_Then404NotFound() throws Exception {
        mockMvc.perform(get(URI + "/11")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Get user failed. User by id=11 not found.")));
    }

    @Test
    public void getUser_WhenIdIsNotInteger_Then400BadRequest() throws Exception {
        mockMvc.perform(get(URI + "/12blah21blah")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Bad Request.")));
    }

    //Cases: Get(GET) user-list by query-params 'page' and 'size'
    @Test
    public void getUserList_WhenDefaultParams_ThenOKWith4Users() throws Exception {
        mockMvc.perform(get(URI)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(usersPerPage)));
    }

    @Test
    public void getUserList_WhenPage3_ThenOKWith2Users() throws Exception {
        mockMvc.perform(get(URI + "?page=3")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void getUserList_WhenPage4_Then404NotFound() throws Exception {
        mockMvc.perform(get(URI + "?page=4")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("No user found.")));
    }

    @Test
    public void getUserList_WhenPage1Size6_ThenOkWith6Users() throws Exception {
        mockMvc.perform(get(URI + "?page=1&size=6")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(6)))
                .andExpect(jsonPath("$.totalUsers", is(10)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.usersPerPage", is(6)))
                .andExpect(jsonPath("$.maxUsersPerPage", is(6)))
                .andExpect(jsonPath("$.['_links'].['firstPage'].['href']", is(HTTP_URL + "?page=1&size=6")))
                .andExpect(jsonPath("$.['_links'].['lastPage'].['href']", is(HTTP_URL + "?page=2&size=6")))
                .andExpect(jsonPath("$.['_links'].['prevPage']").doesNotExist())
                .andExpect(jsonPath("$.['_links'].['nextPage'].['href']", is(HTTP_URL + "?page=2&size=6")));
    }

    @Test
    public void getUserList_WhenPage2Size6_ThenOkWith4Users() throws Exception {
        mockMvc.perform(get(URI + "?page=2&size=6")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.totalUsers", is(10)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.usersPerPage", is(4)))
                .andExpect(jsonPath("$.maxUsersPerPage", is(6)))
                .andExpect(jsonPath("$.['_links'].['firstPage'].['href']", is(HTTP_URL + "?page=1&size=6")))
                .andExpect(jsonPath("$.['_links'].['lastPage'].['href']", is(HTTP_URL + "?page=2&size=6")))
                .andExpect(jsonPath("$.['_links'].['prevPage'].['href']", is(HTTP_URL + "?page=1&size=6")))
                .andExpect(jsonPath("$.['_links'].['nextPage']").doesNotExist());
    }

    @Test
    public void getUserList_WhenPage2Size4_ThenOkWith4Users() throws Exception {
        String excepted = expectedValues.getProperty("getUserList_WhenPage2Size4_ThenOkWith4Users");
        mockMvc.perform(get(URI + "?page=2&size=4")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(excepted))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.totalUsers", is(10)))
                .andExpect(jsonPath("$.totalPages", is(3)))
                .andExpect(jsonPath("$.usersPerPage", is(4)))
                .andExpect(jsonPath("$.maxUsersPerPage", is(4)))
                .andExpect(jsonPath("$.['_links'].['firstPage'].['href']", is(HTTP_URL + "?page=1&size=4")))
                .andExpect(jsonPath("$.['_links'].['lastPage'].['href']", is(HTTP_URL + "?page=3&size=4")))
                .andExpect(jsonPath("$.['_links'].['prevPage'].['href']", is(HTTP_URL + "?page=1&size=4")))
                .andExpect(jsonPath("$.['_links'].['nextPage'].['href']", is(HTTP_URL + "?page=3&size=4")));
    }

    @Test
    public void getUserList_WhenPageIsNegativeOrSizeIsNegative_Then400BadRequest() throws Exception {
        String exceptedMessage = "Invalid page and/or size parameteres. They must be positive.";
        mockMvc.perform(get(URI + "?page=-2&size=6")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is(exceptedMessage)));

        mockMvc.perform(get(URI + "?page=2&size=-6")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is(exceptedMessage)));
    }

    @Test
    public void getUserList_WhenPageNaNOrSizeNaN_Then500InternalError() throws Exception {
        //NaN - Not a Number
        String exceptedMessage = "Bad Request.";
        mockMvc.perform(get(URI + "?page=12blah&size=6")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is(exceptedMessage)));

        mockMvc.perform(get(URI + "?page=2&size=21blah")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is(exceptedMessage)));
    }

    //Cases: Delete(DELETE) user by path-variable 'id'
    @Test
    public void deleteUser_WhenId5_ThenOkWithExceptedJSON() throws Exception {
        String excepted = expectedValues.getProperty("deleteUser_WhenId5_ThenOkWithExceptedJSON");

        mockMvc.perform(delete(URI + "/5")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(excepted));

        mockMvc.perform(get(URI + "/5")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Get user failed. User by id=5 not found.")));
    }

    @Test
    public void deleteUser_WhenNotExistId11_Then404NotFound() throws Exception {
        mockMvc.perform(delete(URI + "/11")
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Deletion failed. User by id=11 not found.")));
    }

    //Cases: Update(PUT) user by path-variable 'id' and JSON(UserDto)
    @Test
    public void putUser_WhenNotExistId11WithOkJSON_Then404NotFound() throws Exception {
        mockMvc.perform(put(URI + "/11")
                .contentType(contentType)
                .content(objectToJSON(testUserDto))
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Update failed. User by id=11 not found.")));
    }

    @Test
    public void putUser_WhenId10WithOkJSON_ThenOkWithExpectedJSON() throws Exception {
        String expected = expectedValues.getProperty("putUser_WhenId10WithOkJSON_ThenOkWithExpectedJSON");
        MvcResult mvcResult = 
            mockMvc.perform(put(URI + "/10")
                    .contentType(contentType)
                    .content(objectToJSON(testUserDto))
                    .accept(contentType))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(content().string(expected))
                    .andReturn();
        
        String json = mvcResult.getResponse().getContentAsString();
        UserDto userDto = JSONToUserDto(json);

        String userId = "" + userDto.getUserId();
        mockMvc.perform(get(URI + "/" + userId)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.['userName']", is(testUserDto.getUserName())))
                .andExpect(jsonPath("$.['firstName']", is(testUserDto.getFirstName())))
                .andExpect(jsonPath("$.['lastName']", is(testUserDto.getLastName())))
                .andExpect(jsonPath("$.['email']", is(testUserDto.getEmail())))
                .andExpect(jsonPath("$.['salary']", is(testUserDto.getSalary().doubleValue())))
                .andExpect(jsonPath("$.['familyMembersCount']", is(testUserDto.getFamilyMembersCount())))
                .andExpect(jsonPath("$.['birthDate']", is(testUserDto.getBirthDate().toString())))
                .andExpect(jsonPath("$.['sex']", is(testUserDto.getSex().toString().toLowerCase())))
                .andExpect(jsonPath("$.['_links'].['self'].['href']", is(HTTP_URL + "/10")));
    }

    @Test
    public void putUser_WhenId10WithEmptyUsername_Then400BadRequest() throws Exception {
        //set userName to empty
        testUserDto.setUserName("");

        mockMvc.perform(put(URI + "/10")
                .contentType(contentType)
                .content(objectToJSON(testUserDto))
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void putUser_WhenId10WithoutUsernameField_Then400BadRequest() throws Exception {
        String requestJSON = expectedValues.getProperty("putUser_WhenId10WithoutUsernameField_Then400BadRequest");
        mockMvc.perform(put(URI + "/10")
                .contentType(contentType)
                .content(requestJSON)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void putUser_WhenId10InvalidEmail_Then400BadRequest() throws Exception {
        //set email to invalid one
        testUserDto.setEmail("mark65_abc.com");

        mockMvc.perform(put(URI + "/10")
                .contentType(contentType)
                .content(objectToJSON(testUserDto))
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void putUser_WhenId10InvalidTypeForSingleProperty_Then400BadRequest() throws Exception {
        //boolean single = 'ok' as a value
        String requestJSON = expectedValues.getProperty("putUser_WhenId10InvalidTypeForSingleProperty_Then400BadRequest");

        mockMvc.perform(put(URI + "/10")
                .contentType(contentType)
                .content(requestJSON)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Some field(s) have a an invalid data type.")));
    }

    @Test
    public void putUser_WhenId10WithAlreadyExistUserName_Then404Conflict() throws Exception {
        String userName = testUserDto.getUserName();
        String json = objectToJSON(testUserDto);
        mockMvc.perform(put(URI + "/9")
                .contentType(contentType)
                .content(json)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        mockMvc.perform(put(URI + "/10")
                .contentType(contentType)
                .content(json)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Update failed. User by userName=" + userName + " does already exist on another user.")));
    }

    //Cases Add(POST) user by JSON(UserDto)
    @Test
    public void postUser_WhenWithOkJSON_ThenOk() throws Exception {
        MvcResult mvcResult
                = mockMvc.perform(post(URI)
                        .contentType(contentType)
                        .content(objectToJSON(testUserDto))
                        .accept(contentType))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(contentType))
                        .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        UserDto userDto = JSONToUserDto(json);

        String userId = "" + userDto.getUserId();
        mockMvc.perform(get(URI + "/" + userId)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.['userName']", is(testUserDto.getUserName())))
                .andExpect(jsonPath("$.['firstName']", is(testUserDto.getFirstName())))
                .andExpect(jsonPath("$.['lastName']", is(testUserDto.getLastName())))
                .andExpect(jsonPath("$.['email']", is(testUserDto.getEmail())))
                .andExpect(jsonPath("$.['salary']", is(testUserDto.getSalary().doubleValue())))
                .andExpect(jsonPath("$.['familyMembersCount']", is(testUserDto.getFamilyMembersCount())))
                .andExpect(jsonPath("$.['birthDate']", is(testUserDto.getBirthDate().toString())))
                .andExpect(jsonPath("$.['sex']", is(testUserDto.getSex().toString().toLowerCase())))
                .andExpect(jsonPath("$.['_links'].['self'].['href']", is(HTTP_URL + "/" + userId)));
    }

    @Test
    public void postUser_WhenWithEmptyUsername_Then400BadRequest() throws Exception {
        //set userName to empty
        testUserDto.setUserName("");

        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(objectToJSON(testUserDto))
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void postUser_WhenWithoutUsernameField_Then400BadRequest() throws Exception {
        String requestJSON = expectedValues.getProperty("postUser_WhenWithoutUsernameField_Then400BadRequest");
        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(requestJSON)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void postUser_WhenInvalidEmail_Then400BadRequest() throws Exception {
        //set email to invalid one
        testUserDto.setEmail("mark65_abc.com");

        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(objectToJSON(testUserDto))
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Validation failed.")));
    }

    @Test
    public void postUser_WhenInvalidTypeForSingleProperty_Then400BadRequest() throws Exception {
        //boolean single = 'ok' as a value
        String requestJSON = expectedValues.getProperty("putUser_WhenId10InvalidTypeForSingleProperty_Then400BadRequest");

        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(requestJSON)
                .accept(contentType))
                .andDo(print())
                //.andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Some field(s) have a an invalid data type.")));
    }

    @Test
    public void postUser_WhenWithAlreadyExistUserName_Then404Conflict() throws Exception {
        String userName = testUserDto.getUserName();
        String json = objectToJSON(testUserDto);
        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(json)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType));

        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(json)
                .accept(contentType))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Save failed. User by userName=" + userName + " does already exist on another user.")));
    }

}
