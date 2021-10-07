package ru.bogdanov.itemservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogdanov.itemservice.entity.Item;

import java.util.List;

public interface ItemRepozitory extends JpaRepository<Item,Long> {
    List<Item> findAllByType(String type);

    List<Item> findAllByManufacturer(String manufacturer);

    List<Item> findAllByAvailability(boolean availability);

    List<Item> findAllByOrderByPrice();

    List<Item> findAllByTypeAndManufacturerAndAvailability(String type,String manufacturer, boolean availability);
    List<Item> findAllByTypeAndManufacturer(String type,String manufacturer);
}
