package cn.terrylam.chariot.api.controller;

import cn.terrylam.chariot.api.BaseSpringBootTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldControllerTest extends BaseSpringBootTest {
	
	@Autowired
	private HelloWorldController helloWorldController;
	
	private MockMvc mockMvc;
	    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
    }
	    

    /**
     * 测试示例
     * @throws Exception 
     */
    @Test
    public void helloTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello")
        							.accept(MediaType.APPLICATION_JSON)/*.param("paramName", "paramValue")*/)
                                     .andExpect(MockMvcResultMatchers.status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();
        
        logger.info(mvcResult.getResponse().getContentAsString());
    }
}
