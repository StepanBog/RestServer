package ru.bogdanov.bucketservice.controller;

import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bogdanov.bucketservice.entity.Bucket;
import ru.bogdanov.bucketservice.repository.BucketRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bucket/{bucket_id}")
public class BucketController {

    private final BucketRepository bucketRepository;

    public BucketController(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }


    @GetMapping("get_bucket")
    public Bucket getByID(@PathVariable("bucket_id") Bucket bucket){
        return bucket;
    }

    @PostMapping("post_bucket")
    public Bucket addOrClearBucket(@PathVariable("bucket_id") String id) {
        Bucket bucket = new Bucket();
        bucket.setId(id);
        return bucketRepository.save(bucket);
        //   return "success";
    }
    @PostMapping("add_item/{item_id}")
    public Bucket addItem(@PathVariable("bucket_id") Bucket bucket,@PathVariable("item_id") Long item_id) {
        bucket.addItem(item_id);
        return bucketRepository.save(bucket);
        //   return "success";
    }
    @PostMapping("set_address")
    public Bucket setAddrss(@PathVariable("bucket_id") Bucket bucket,@RequestBody String address) {
        bucket.setDelivery_address(address);
        Bucket b = bucketRepository.save(bucket);
        return b;
        //   return "success";
    }
    @DeleteMapping("delete_item/{item_id}")
    public Bucket deleteItem(@PathVariable("bucket_id") Bucket bucket,@PathVariable("item_id") Long item_id){
        bucket.deleteItem(item_id);
        return bucketRepository.save(bucket);
    }


}
