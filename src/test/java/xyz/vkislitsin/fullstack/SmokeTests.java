package xyz.vkislitsin.fullstack;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.vkislitsin.fullstack.web.IndexController;

@SpringBootTest
public class SmokeTests {

    @Autowired
    private IndexController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
