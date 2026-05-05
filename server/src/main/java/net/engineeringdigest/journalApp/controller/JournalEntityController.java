package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {
    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesByUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntity> allJournal = user.getJournalEntities();
        if(allJournal !=null && !allJournal.isEmpty())
            return new ResponseEntity<>(allJournal, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{userName}")
    public boolean createJournal(@RequestBody JournalEntity journalEntity,@PathVariable String userName){
       try{
           User user = userService.findByUserName(userName);

           journalService.saveEntity(journalEntity,userName);
       }catch (Exception e){

       }
        return journalService.createJournal(journalEntity);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJornalEntityById(@PathVariable ObjectId id){
        Optional<JournalEntity> journalEntity = journalService.getJournal(id);
        if(journalEntity.isPresent())
        return new ResponseEntity<>(journalEntity, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName,@PathVariable  ObjectId id) {
         journalService.deleteJournal(userName,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/id/{userName}/{id}")
    public JournalEntity updateJournalById(@PathVariable ObjectId id,@PathVariable String userName,@RequestBody JournalEntity newEntity){
        return journalService.updateJournal(id,newEntity,userName);
    }
}
