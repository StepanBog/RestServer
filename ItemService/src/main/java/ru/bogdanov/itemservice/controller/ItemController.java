package ru.bogdanov.itemservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bogdanov.itemservice.entity.Item;
import ru.bogdanov.itemservice.repository.ItemRepozitory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("item")
public class ItemController {
    private final ItemRepozitory itemRepozitory;
    @Autowired
    public ItemController(ItemRepozitory itemRepozitory) {
        this.itemRepozitory = itemRepozitory;
    }

    @GetMapping("{id}")
    public Item getById(@PathVariable("id") Item item){
        return item;
    }
    @GetMapping
    public List<Item> getAll(){
        return itemRepozitory.findAll();
    }

    @GetMapping("filtered_by_type/{type}")
    public List<Item> getByType(@PathVariable(name = "type") String filter){
        return itemRepozitory.findAllByType(filter);
    }
    @GetMapping("filtered_by_manufacturer/{manufacturer}")
    public List<Item> getByManufacturer(@PathVariable(name = "manufacturer") String filter){
        return itemRepozitory.findAllByManufacturer(filter);
    }
    @GetMapping("filtered_by_availability/{availability}")
    public List<Item> getByAvailability(@PathVariable(name = "availability") String filter){
       return itemRepozitory.findAllByAvailability(Boolean.parseBoolean(filter));
    }
    @GetMapping("sorted_by_price")
    public List<Item> getSortedByPrice() {
        return itemRepozitory.findAllByOrderByPrice();
    }
  /* @GetMapping()
    public List<Item> getWithFilter(HttpServletRequest request){
        String type = request.getParameter("type");
        String manufacturer = request.getParameter("manufacturer");
        String availability = request.getParameter("availability");
        if (type == null)
            type = "*";
        if (manufacturer == null)
            manufacturer = "*";
        if (availability == null)
            return itemRepozitory.findAllByTypeAndManufacturer(type,manufacturer);
        else
            return itemRepozitory.findAllByTypeAndManufacturerAndAvailability(type,manufacturer,Boolean.parseBoolean(availability));
    }*/
}
