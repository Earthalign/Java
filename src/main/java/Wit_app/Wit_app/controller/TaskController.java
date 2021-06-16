package Wit_app.Wit_app.controller;


import Wit_app.Wit_app.model.TaskRepository;
import Wit_app.Wit_app.model.Task;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    // Wstrzyknij tutaj działa:  @Autowired
    TaskController(final TaskRepository repository) { this.repository = repository;           //Dzięki temu spring otworzy konstruktor
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate){
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
       // @RequestMapping(method = RequestMethod.GET, path = "/tasks")
     @GetMapping(value = "/tasks", params = {"!sort","!page", "!size"})
        ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the Tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
       return repository.findById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?>updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if(!repository.existsById(id)) {
    return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }
    //sygnał dla springa, że na początku transakcja rozpoczęcie a potem zatwierdzenie - ta metoda musi być publiczna.
    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?>toggleTask(@PathVariable int id) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
     repository.findById(id)
             .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

    /*/
    List<Task> readAllTasks(){
    logger.warn("Exposing all the Tasks!");
    return repository.findAll());
}   */

    /*@GetMapping("/tasks{id}")
    ResponseEntity<List<Task>> updateTasks(Pageable page){
        logger.info("Custom Pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
*/

}
