package com.c2.demo.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")  //maps entity to collection
@Getter  // project lombok
@Setter
public class User {
    @Id  //maps attribute to _id of mongodb (which is unique for each document)
    private ObjectId id;
    @Indexed(unique = true)  //indexes are  not automatically made in db by default after this annotation, see application properties
    @NonNull
    private String name;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
