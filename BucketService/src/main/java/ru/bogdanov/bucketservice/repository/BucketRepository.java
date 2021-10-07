package ru.bogdanov.bucketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogdanov.bucketservice.entity.Bucket;

public interface BucketRepository extends JpaRepository<Bucket,Long> {

}
