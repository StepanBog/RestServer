package ru.bogdanov.orderservice.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.bogdanov.orderservice.entity.Bucket;
import ru.bogdanov.orderservice.entity.ClientOrder;
import ru.bogdanov.orderservice.entity.OrderStatus;
import ru.bogdanov.orderservice.repository.OrderRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import java.util.Arrays;
import java.util.List;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import java.util.Optional;

@RestController
@RequestMapping("order/{client_id}")
public class OrderController {
    private final OrderRepository orderRepository;

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private Environment env;


    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("get_order/{id}")
    public Optional<ClientOrder> getOrderById(@PathVariable("id") Long id){
        return orderRepository.findById(Long.valueOf(id));
    }
    @PostMapping("create_order")
    public ClientOrder postOrder(@PathVariable("client_id") Long client_id)
    {
        Bucket bucket = getBucketByClient(client_id);
        if (bucket.getDelivery_address() == null || bucket.getDelivery_address().equals(""))
            return null;
        ClientOrder order = new ClientOrder();
        order.setClient(client_id);
        order.setStatus(OrderStatus.WAIT_PAY);
        order.setItems(bucket.getItems());
        order.setDeliveryAddress(bucket.getDelivery_address());
        return orderRepository.save(order);
    }

    @GetMapping("get_orders")
    public List<ClientOrder> getAllOrders(@PathVariable("client_id") Long id) {
        return orderRepository.findAllByClient(id);
    }
    @GetMapping("get_waiting_orders")
    public List<ClientOrder> getWaitingOrders(@PathVariable("client_id") Long id) {
        return orderRepository.findAllByStatusAndClient(OrderStatus.WAIT_PAY,id);
    }
    @GetMapping("get_payed_orders")
    public List<ClientOrder> getPayedOrders(@PathVariable("client_id") Long id) {
        return orderRepository.findAllByStatusAndClient(OrderStatus.PAYED,id);
    }
    @GetMapping("get_on_way_orders")
    public List<ClientOrder> getOnWayOrders(@PathVariable("client_id") Long id) {
        return orderRepository.findAllByStatusAndClient(OrderStatus.ON_WAY,id);
    }
    @GetMapping("get_arrived_orders")
    public List<ClientOrder> getArrivedOrders(@PathVariable("client_id") Long id) {
        return orderRepository.findAllByStatusAndClient(OrderStatus.ARRIVED,id);
    }

    private Bucket getBucketByClient(Long bucket_id) {
        RestTemplate restTemplate = new RestTemplate();
        List<String> applications = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances(applications.get(applications.indexOf("bucket-service")));
        String url =  instances.get(0).getUri() + "/bucket/" + bucket_id.toString() + "/get_bucket";;
        // String url = "http://BUCKET-SERVICE/bucket/" + id.toString();


        HttpEntity<String> request2 =
                new HttpEntity<>(getHeaders());
        ResponseEntity<Bucket> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request2, Bucket.class);
        return responseEntity.getBody();
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
