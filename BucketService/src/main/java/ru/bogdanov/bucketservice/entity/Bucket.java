package ru.bogdanov.bucketservice.entity;

import ru.bogdanov.bucketservice.converter.ListToStringConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Bucket {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name="items")
    @Convert(converter = ListToStringConverter.class)
    private List<Long> items;
    private String delivery_address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
