package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SuppelierService;
import com.mycompany.myapp.service.dto.SuppelierDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SuppelierResource {

    private final SuppelierService service;

    public SuppelierResource(SuppelierService service) {
        this.service = service;
    }
      // LIST
      @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_ALL')")
      @GetMapping("/suppeliers")
      public List<SuppelierDTO> list() {
          return service.getAll();
      }
       // VIEW
        @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_ID')")
        @GetMapping("/suppelier/{id}")
        public SuppelierDTO view(@PathVariable String id) {
            return service.getById(id);
        }
     // CREATE
    @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_CREATE')")
    @PostMapping("/suppelier")
    public ResponseEntity<SuppelierDTO> create(@RequestBody SuppelierDTO dto) throws URISyntaxException {
        SuppelierDTO result = service.create(dto);
        return ResponseEntity.ok(result);
    }
    // EDIT (UPDATE)
    @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_UPDATE')")
    @PutMapping("/suppelier/{id}")
    public ResponseEntity<SuppelierDTO> update(@PathVariable String id, @RequestBody SuppelierDTO dto) {
        dto.setSuppelierId(id);
        SuppelierDTO result = service.update(dto);
        return ResponseEntity.ok(result);
    }

    // DELETE (soft)
    @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_DELETE')")
    @DeleteMapping("/suppelier/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("@permissionEvaluator.hasAuthority('SUPPELIER_USERID')")
    @GetMapping("/suppelier/user/{userId}")
    public ResponseEntity<List<SuppelierDTO>> getUserId(@PathVariable Long userId) {
        List<SuppelierDTO> result = service.getAllUserId(userId);
        return ResponseEntity.ok(result);
    }
    
}