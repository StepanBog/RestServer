package ru.bogdanov.clientservice.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.bogdanov.clientservice.entity.Client;
import ru.bogdanov.clientservice.repozitory.ClientRepozitory;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("client/{user_id}")
public class ClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private final ClientRepozitory clientRepozitory;

  //  @Autowired
   // private RestTemplate restTemplate;

    public ClientController(ClientRepozitory clientRepozitory) {
        this.clientRepozitory = clientRepozitory;
    }

    @GetMapping("get_client")
    public Client getByID(@PathVariable("user_id") Client client){
        return client;
    }

    @PostMapping("post_client")
    public Client addClient(@PathVariable("user_id") String id,@RequestBody Client client) {
        postBucket(id);
        client.setId(id);
        return clientRepozitory.save(client);
     //   return "success";
    }

    @Autowired
    private Environment env;

    private void postBucket(String id){
        RestTemplate restTemplate = new RestTemplate();
        List<String> applications = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances(applications.get(applications.indexOf("bucket-service")));
        String url =  instances.get(0).getUri() + "/bucket/" + id.toString() + "/post_bucket";;
       // String url = "http://BUCKET-SERVICE/bucket/" + id.toString();


        HttpEntity<String> request2 =
                    new HttpEntity<>(getHeaders());
        // restTemplate.postForLocation(url,request2);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, request2, Object.class);
    }

    private HttpHeaders getHeaders ()
    {
        String adminuserCredentials = env.getProperty("system.username") + ":" + env.getProperty("system.password");
        String encodedCredentials =
                new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

}
