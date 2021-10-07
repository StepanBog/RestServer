package ru.bogdanov.proxyserver.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bogdanov.proxyserver.entity.User;
import ru.bogdanov.proxyserver.entity.services.Bucket;
import ru.bogdanov.proxyserver.entity.services.Client;
import ru.bogdanov.proxyserver.entity.services.ClientOrder;
import ru.bogdanov.proxyserver.entity.services.Item;

import java.util.Arrays;
import java.util.List;

@Service
public class ProxyService {
    @Autowired
    private DiscoveryClient discoveryClient;
    public Client redirToClient(String method, String request, Client client, User user){
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("client",request,user,true);
        if (method.equals("GET")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<Client> response = restTemplate.exchange(url, HttpMethod.GET, request2, Client.class);
            return response.getBody();
        } else if (method.equals("POST")) {
            HttpEntity request2 = new HttpEntity(client,getHeaders());
            ResponseEntity<Client> response = restTemplate.exchange(url, HttpMethod.POST, request2, Client.class);
            return response.getBody();
        }
        return null;
    }

    public Item redirToItem(String request,Long id){
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("item",request,null,false);
        HttpEntity request2 = new HttpEntity(getHeaders());
        ResponseEntity<Item> response = restTemplate.exchange(url, HttpMethod.GET, request2, Item.class);
        return response.getBody();

    }
    public List<Item> redirToItemList(String request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("item",request,null,false);
        HttpEntity request2 = new HttpEntity(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request2, List.class);
        return response.getBody();
    }

    public Bucket redirToBucket(String method, String request,String address,User user){
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("bucket",request,user,true);
        if (method.equals("GET")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<Bucket> response = restTemplate.exchange(url, HttpMethod.GET, request2, Bucket.class);
            return response.getBody();
        } else  if (method.equals("POST")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            if (address != null) {
                request2 = new HttpEntity(address,getHeaders());
            }
            ResponseEntity<Bucket> response = restTemplate.exchange(url, HttpMethod.POST, request2, Bucket.class);
            return response.getBody();
        } else if (method.equals("DELETE")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<Bucket> response = restTemplate.exchange(url, HttpMethod.DELETE, request2, Bucket.class);
            return response.getBody();
        }
        return null;
    }

    public List<Long> redirToBucketList(String method, String request,User user) {
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("bucket",request,user,true);
        if (method.equals("GET")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request2, List.class);
            return response.getBody();
        }
        return null;
    }

    private String createURL(String service, String address,User user,boolean needID) {
        List<String> applications = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances(applications.get(applications.indexOf(service +"-service")));
        // String url = "http://" + service.toUpperCase() + "-SERVICE" + address;//
        String url = "";
        address = address.substring(1);
        int pos = address.indexOf('/');
        if (pos >=0)
            address = address.substring(pos);
        else
            address = "";
        if (needID){
            return instances.get(0).getUri()+"/" + service + "/" + user.getId() + address;
        } else
            return instances.get(0).getUri()+"/" + service + address;

    //    else
      //      return instances.get(0).getUri()+"/" + service + address;
        //user.getId()
    }

    @Autowired
    private Environment env;

    private HttpHeaders getHeaders()
    {
        String adminuserCredentials = env.getProperty("system.username") + ":" + env.getProperty("system.password");
        String encodedCredentials =
                new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
      //  httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }


    public ClientOrder redirectionToOrder(String method, String request, User user) {
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("order",request,user,true);
        if (method.equals("GET")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<ClientOrder> response = restTemplate.exchange(url, HttpMethod.GET, request2, ClientOrder.class);
            return response.getBody();
        } else if (method.equals("POST")) {
            HttpEntity request2 = new HttpEntity(getHeaders());
            ResponseEntity<ClientOrder> response = restTemplate.exchange(url, HttpMethod.POST, request2, ClientOrder.class);
            return response.getBody();
        }
        return null;
    }

    public List<ClientOrder> redirectionToOrderList(String method, String request, User user) {
        RestTemplate restTemplate = new RestTemplate();
        String url = createURL("order",request,user,true);
        HttpEntity request2 = new HttpEntity(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request2, List.class);
        return response.getBody();
    }
}
