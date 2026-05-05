package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;
    @Autowired
    private UserService userService;

    public boolean createJournal(JournalEntity journalEntity){
        journalEntity.setDate(LocalDateTime.now());
        JournalEntity save = journalRepo.save(journalEntity);
        return true;
    }

    public List<JournalEntity> getAllJournal() {
        return journalRepo.findAll();
    }

    public Optional<JournalEntity> getJournal(ObjectId id) {
        return journalRepo.findById(String.valueOf(id));
    }

    public boolean deleteJournal(String userName, ObjectId id) {
        User user = userService.findByUserName(userName);
        user.getJournalEntities().removeIf(x->x.getId().equals(id));
        userService.saveUser(user);
        journalRepo.deleteById(String.valueOf(id));
        return true;
    }

    public JournalEntity updateJournal(ObjectId id, JournalEntity newEntity, String userName) {
        JournalEntity old = journalRepo.findById(String.valueOf(id)).orElse(null);
        if(old != null){
            old.setTitle(newEntity.getTitle() !=null && !newEntity.getTitle().isEmpty() ? newEntity.getTitle() : old.getTitle());
            old.setContent(newEntity.getContent() !=null && !newEntity.getContent().isEmpty() ? newEntity.getContent() : old.getContent());
        }
        if (old != null) {
            createJournal(old);
        }
        return old;
    }

    public void saveEntity(JournalEntity journalEntity, String userName) {
        User user = userService.findByUserName(userName);
        journalEntity.setDate(LocalDateTime.now());
        JournalEntity saved = journalRepo.save(journalEntity);
        user.getJournalEntities().add(saved);
        userService.saveUser(user);
    }
}
