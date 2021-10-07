package ru.bogdanov.orderservice.entity;

import ru.bogdanov.orderservice.converter.ListToStringConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long client;

    @Column(name="items")
    @Convert(converter = ListToStringConverter.class)
    private List<Long> items;
    private String deliveryAddress;

    // private String items;
    private OrderStatus status;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
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
