package com.c2.demo.controller;

import com.c2.demo.entity.JournalEntry;
import com.c2.demo.entity.User;
import com.c2.demo.service.JournalEntryService;
import com.c2.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//controller -> service -> repository
//Controller handles HTTP layer (routing, request/response), Service handles business logic.

@RequestMapping("v2/journals")
@RestController
public class JournalEntryControllerv2 {

    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        List<JournalEntry> userEntries = journalEntryService.getAllEntries(userName);
        if(userEntries==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntries, HttpStatus.OK);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry newEntry, @PathVariable String userName){
        try{
            JournalEntry savedEntry = journalEntryService.saveEntry(newEntry, userName);
            if(savedEntry==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{requestedEntryId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId requestedEntryId){
        Optional<JournalEntry> entry = journalEntryService.getEntryById(requestedEntryId);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{requestedEntryId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId requestedEntryId, @PathVariable String userName){
        journalEntryService.deleteEntryById(requestedEntryId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{entryToBeUpdatedId}")
    public ResponseEntity<?> updateEntryById(@RequestBody JournalEntry entryToBeUpdated,
                                             @PathVariable ObjectId entryToBeUpdatedId,
                                             @PathVariable String userName)
    {
        JournalEntry entry = journalEntryService.updateEntryById(entryToBeUpdatedId, entryToBeUpdated, userName);
        if(entry==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }
}
