package com.barinek.uservice.backlog;

import com.barinek.uservices.restsupport.RestTestSupport;
import com.barinek.uservices.schema.TestDataSource;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StoryControllerTest extends RestTestSupport {
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

        String json = "{\"projectId\":2,\"name\":\"An epic story\"}"; // account from fixtures
        String response = doPost("http://localhost:8082/stories", json);

        Story actual = new ObjectMapper().readValue(response, Story.class);
        assertEquals(actual.getProjectId(), 2);
        assertEquals(actual.getName(), "An epic story");
    }

    @Test
    public void testList() throws Exception {
        TestDataSource.cleanWithFixtures();

        String response = doGet("http://localhost:8082/stories", new BasicNameValuePair("projectId", "1")); // account and projects from fixtures

        List<Story> stories = new ObjectMapper().readValue(response, new TypeReference<List<Story>>() {
        });
        assertEquals(2, stories.size());
        assertEquals(1, stories.get(0).getProjectId());
        assertEquals(1, stories.get(1).getProjectId());
    }
}