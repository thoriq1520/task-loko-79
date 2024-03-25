package com.example.infoloko.repository;


import com.example.infoloko.model.InfoLoko;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLokoRepository extends MongoRepository<InfoLoko, String> {
}
