package tacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.config.ConfigServerProperties;

@RestController
@RequestMapping("/configServer")
public class ConfigServerTestController {

    @Autowired
    private ConfigServerProperties configServerProperties;

    @GetMapping("/message")
    public String getConfigMessage() {
        return configServerProperties.getMessage();
    }
}
