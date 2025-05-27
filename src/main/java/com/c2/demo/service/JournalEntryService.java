package com.c2.demo.service;

import com.c2.demo.entity.JournalEntry;
import com.c2.demo.entity.User;
import com.c2.demo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.getUserByUserName(userName);
            if(user==null){
                return null;
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
            return savedEntry;
        }
        catch (Exception e){
//            In a @Transactional method, always throw a RuntimeException when a failure occurs.
//            Spring only rolls back transactions for unchecked exceptions by default.
//            Silent failures (e.g., logs without exceptions) will not trigger a rollback.
            throw new RuntimeException("An error occurred while saving entry", e);
        }
    }

    public List<JournalEntry> getAllEntries(String userName) {
        User user = userService.getUserByUserName(userName);
        if(user==null){
            return null;
        }
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getEntryById(ObjectId requestedEntryId) {
        return journalEntryRepository.findById(requestedEntryId);
    }

    public void deleteEntryById(ObjectId entryId, String userName) {
        User user = userService.getUserByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(entryId));
        userService.saveUser(user);
        journalEntryRepository.deleteById(entryId);
    }

    public JournalEntry updateEntryById(ObjectId entryId, JournalEntry newEntry, String userName) {
        JournalEntry oldEntry = journalEntryRepository.findById(entryId).orElse(null);
        if (oldEntry != null) {
            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                oldEntry.setContent(newEntry.getContent());
            }
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                oldEntry.setTitle(newEntry.getTitle());
            }
            journalEntryRepository.save(oldEntry);
        }
        return oldEntry;
    }
}
