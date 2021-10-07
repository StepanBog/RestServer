package ru.bogdanov.orderservice.entity;


import java.util.List;

public class Bucket {

    private Long id;

    private List<Long> items;
    private String delivery_address;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getItems() {
        return items;
    }

    public void setItems(List<Long> items) {
        this.items = items;
    }

    public void addItem(Long item_id) {
        items.add(item_id);
    }

    public void deleteItem(Long item_id) {
        items.remove(item_id);
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
}
