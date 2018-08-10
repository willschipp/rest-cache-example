package com.example.code.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/cache")
public class CachedInvoker {

    private static final Log logger = LogFactory.getLog(CachedInvoker.class);

    @Value("${cached.url}")
    private String url;

    @Cacheable
    @RequestMapping(method= RequestMethod.GET)
    public Object get() throws Exception {
        RestTemplate template = new RestTemplate();
        logger.info("invoked");
        return template.getForObject(new URI(url),String.class);
    }
}
