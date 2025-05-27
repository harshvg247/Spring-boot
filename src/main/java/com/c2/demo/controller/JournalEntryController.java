package com.c2.demo.controller;

import com.c2.demo.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

//controller -> service -> repository

@RequestMapping("v1/journals")
@RestController
public class JournalEntryController {
    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry newEntry){
        journalEntries.put(newEntry.getId(), newEntry);
        return true;
    }

    @GetMapping("id/{requestedEntryId}")
    public JournalEntry getEntryById(@PathVariable ObjectId requestedEntryId){
        return journalEntries.get(requestedEntryId);
    }

    @DeleteMapping("id/{requestedEntryId}")
    public JournalEntry deleteEntryById(@PathVariable ObjectId requestedEntryId){
        return journalEntries.remove(requestedEntryId);
    }

    @PutMapping("id/{entryToBeUpdatedId}")
    public JournalEntry updateEntryById(@RequestBody JournalEntry entryToBeUpdated, @PathVariable ObjectId entryToBeUpdatedId){
        return journalEntries.put(entryToBeUpdatedId, entryToBeUpdated);
    }
}
