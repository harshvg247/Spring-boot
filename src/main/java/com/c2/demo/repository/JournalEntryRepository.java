package com.c2.demo.repository;


import com.c2.demo.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


//When autowiring a service class,
//we use @Component (or @Service) to explicitly tell Spring to create and manage that class as a bean.
//However, when autowiring a repository interface, we usually don't need @Component
//because Spring Data automatically detects interfaces that extend MongoRepository (or other Spring Data interfaces)
//and creates proxy implementations for them at runtime.
//These proxies are automatically registered as beans in the Spring container.
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
