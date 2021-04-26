package au.com.gsn.daisee;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.gsn.daisee.service.AppConfig;
import au.com.gsn.daisee.service.DaiseeAPIService;

@RunWith(SpringRunner.class)
@RestClientTest(value={DaiseeAPIService.class, AppConfig.class})
public class DaiseeApplicationTests {

    @Autowired
    private DaiseeAPIService service;

    @Test
    public void usersNotEmpty() throws Exception {

     
    }

 
}