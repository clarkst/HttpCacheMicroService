package metapack;

import metapack.model.Address;
import metapack.model.StoreLocation;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest()
public class ApplicationIntegrationTests {

    public static final String HTTP_LOCALHOST_8080_API_LOCATIONS_CITY = "http://localhost:8080/api/v1/locations?city=";
    private static final String SEARCH_CITY_VALUE = "Bristol";
    public static final String CITY_PROPERTY = "city";
    public static final String ADDRESS_PROPERTY = "address";
    public static final String UNKNOWN_CITY = "DummyCity";

    @Test
    public void shouldReturnListOfStoreLocationsForExistingSearchCity() {
        RestTemplate testClient = new RestTemplate();
        testClient.setErrorHandler(new DefaultResponseErrorHandler());
        ResponseEntity<StoreLocation[]> responseEntity = testClient.getForEntity(HTTP_LOCALHOST_8080_API_LOCATIONS_CITY + SEARCH_CITY_VALUE, StoreLocation[].class);

        assertTrue("Response code should be successful", responseEntity.getStatusCode().is2xxSuccessful());
        assertThat("Response should have results", responseEntity.getBody(), not(emptyArray()));

        List<StoreLocation> l = Arrays.asList(responseEntity.getBody());
        Matcher<Address> withSearchCity = hasProperty(CITY_PROPERTY, is(SEARCH_CITY_VALUE));
        assertThat("Every result location city should match search city", l, everyItem(hasProperty(ADDRESS_PROPERTY, withSearchCity)));
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldReturnNotFoundForUnknownCity() {
        RestTemplate testClient = new RestTemplate();
        testClient.setErrorHandler(new DefaultResponseErrorHandler());
        try {
            ResponseEntity<StoreLocation[]> responseEntity = testClient.getForEntity(HTTP_LOCALHOST_8080_API_LOCATIONS_CITY + UNKNOWN_CITY, StoreLocation[].class);
        } catch (HttpClientErrorException httpClientErrorException) {
            assertThat("Status code should be 404 Not Found",httpClientErrorException.getStatusCode(), is(HttpStatus.NOT_FOUND));
            throw httpClientErrorException;
        }
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldReturnErrorForXMLAcceptHeader() {

        HttpHeaders requestHeaders = new HttpHeaders();

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_XML);
        requestHeaders.setAccept(acceptableMediaTypes);

        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<StoreLocation[]> responseEntity = restTemplate.exchange(HTTP_LOCALHOST_8080_API_LOCATIONS_CITY + SEARCH_CITY_VALUE, HttpMethod.GET, requestEntity, StoreLocation[].class);

        } catch (HttpClientErrorException httpClientErrorException) {
            assertThat("Status code should be 406 Not Acceptable",httpClientErrorException.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
            throw httpClientErrorException;
        }
    }

}
