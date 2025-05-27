package com.c2.demo.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//@Data of project Lombok enforces argsContructor,
//add @NoArgsContructor along with it for proper working while new entry is being created

@Document(collection = "journal_entries")  //maps entity to collection
@Getter  // project lombok
@Setter
public class JournalEntry {
    @Id  //maps attribute to _id of mongodb (which is unique for each document)
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
}
