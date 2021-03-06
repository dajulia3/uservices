package com.barinek.uservice.allocations;

import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AllocationControllerTest extends RestTestSupport {
    App app;

    @Before
    public void setUp() throws Exception {
        app = new App();
        app.start();
    }

    @After
    public void tearDown() throws Exception {
        app.stop();
    }

    @Test
    public void testCreate() throws Exception {
        TestDataSource.cleanWithFixtures();

        String json = "{\"projectId\":2,\"userId\":1,\"firstDay\":\"2015-05-17\",\"lastDay\":\"2015-05-26\"}"; // project and user from fixtures
        String response = doPost("http://localhost:8081/allocations", json);

        Allocation actual = new ObjectMapper().readValue(response, Allocation.class);
        assertEquals(2, actual.getProjectId());
        assertEquals(1, actual.getUserId());
    }

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8081/allocations", new BasicNameValuePair("projectId", "1")); // allocations from fixtures

        List<Allocation> allocations = new ObjectMapper().readValue(response, new TypeReference<List<Allocation>>() {
        });
        Allocation allocation = allocations.get(0);
        assertEquals(1, allocation.getProjectId());
        assertEquals(1, allocation.getUserId());
    }
}