package ru.bogdanov.proxyserver.entity.services;


import java.util.List;

public class ClientOrder {

    private Long id;
    private String client;

    private List<Long> items;
   // private String items;
    private OrderStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<Long> getItems() {
        return items;
    }

    public void setItems(List<Long> Items) {
        this.items = Items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
