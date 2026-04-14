package de.schule.elearning.interfaces.rest;

import de.schule.elearning.domain.LearningModule;
import de.schule.elearning.domain.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ModuleController {

    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @GetMapping("/modules")
    public List<LearningModule> listAll() {
        return service.findAll();
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<LearningModule> findById(@PathVariable String id) {
        return service.findAll().stream()
            .filter(m -> m.id().equalsIgnoreCase(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
