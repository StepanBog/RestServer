package ru.bogdanov.proxyserver.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.bogdanov.proxyserver.entity.services.Bucket;
import ru.bogdanov.proxyserver.entity.services.Client;
import ru.bogdanov.proxyserver.entity.User;
import ru.bogdanov.proxyserver.entity.services.ClientOrder;
import ru.bogdanov.proxyserver.entity.services.Item;
import ru.bogdanov.proxyserver.service.ProxyService;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping

public class GatewayController {

    @Autowired
    private ProxyService proxyService;


//----------Redirection To Client Service
    @GetMapping({"client/get_client"})
    public Client redirToClientGet(@AuthenticationPrincipal User user, HttpServletRequest request){
       return proxyService.redirToClient(request.getMethod(),request.getRequestURI(),null,user);

    }
    @PostMapping({"client/post_client"})
    public Client redirToClientPost(@AuthenticationPrincipal User user, HttpServletRequest request, @RequestBody Client client){
        return proxyService.redirToClient(request.getMethod(),request.getRequestURI(),client,user);

    }
// ------------Redirection to Item Service    ----
    @GetMapping({"item/{id}"})
    public Item redirToItem(HttpServletRequest request,@PathVariable("id") Long id){
        return proxyService.redirToItem(request.getRequestURI(),id);

    }
    @GetMapping({"item/**"})
    public List<Item> redirToItemLIst(HttpServletRequest request){
        return proxyService.redirToItemList(request.getRequestURI());

    }
//-----------------Redirection to Bucket Service
    @GetMapping({"bucket/get_bucket"})
    public Bucket redirToBucketget_bucket(@AuthenticationPrincipal User user, HttpServletRequest request){
        return proxyService.redirToBucket(request.getMethod(),request.getRequestURI(),null,user);
    }
    @GetMapping({"bucket/get_list"})
    public List<Long> redirToBucketget_list(@AuthenticationPrincipal User user, HttpServletRequest request){
        return  proxyService.redirToBucketList(request.getMethod(),request.getRequestURI(),user);
    }

    @PostMapping({"bucket/add_item/{item_id}"})
    public Bucket redirToBucketadd_item(@AuthenticationPrincipal User user, HttpServletRequest request,@PathVariable("item_id") Long id){
        return proxyService.redirToBucket(request.getMethod(),request.getRequestURI(),null,user);
    }
    @PostMapping({"bucket/set_address"})
    public Bucket redirToBucketSetAddress(@AuthenticationPrincipal User user, HttpServletRequest request,@RequestBody String address){
        return proxyService.redirToBucket(request.getMethod(),request.getRequestURI(),address,user);
    }
    @DeleteMapping({"bucket/delete_item/{item_id}"})
    public Bucket redirToBucketdelete_item(@AuthenticationPrincipal User user, HttpServletRequest request,@PathVariable("item_id") Long id){
        return proxyService.redirToBucket(request.getMethod(),request.getRequestURI(),null,user);
    }

//------------------------
    @GetMapping({"order/get_order/{id}"})
    public ClientOrder redirToOrderGet(@AuthenticationPrincipal User user, HttpServletRequest request){
        return  proxyService.redirectionToOrder(request.getMethod(),request.getRequestURI(),user);
    }
    @GetMapping({"order/**"})
    public List<ClientOrder> redirToOrderGetList(@AuthenticationPrincipal User user, HttpServletRequest request){
        return proxyService.redirectionToOrderList(request.getMethod(),request.getRequestURI(),user);
    }
    @PostMapping({"order/create_order"})
    public ClientOrder redirToOrderPost(@AuthenticationPrincipal User user, HttpServletRequest request){
        return proxyService.redirectionToOrder(request.getMethod(),request.getRequestURI(),user);
    }


}


