package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PermissionQueryService;
import com.mycompany.myapp.service.PermissionService;
import com.mycompany.myapp.service.criteria.PermissionCriteria;
import com.mycompany.myapp.service.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PermissionResource {

    private final PermissionService permissionService;
    private final PermissionQueryService permissionQueryService;

    public PermissionResource(PermissionService permissionService, PermissionQueryService permissionQueryService) {
        this.permissionService = permissionService;
        this.permissionQueryService = permissionQueryService;
    }

    @PostMapping("/permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO result = permissionService.createPermission(permissionDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/permissions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<PermissionDTO>> getPermission(PermissionCriteria criteria, Pageable pageable) {
        Page<PermissionDTO> result = permissionQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable String id) {
        PermissionDTO result = permissionService.getPermission(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        permissionDTO.setId(id);
        PermissionDTO result = permissionService.updatePermission(permissionDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePermission(@PathVariable String id) {
        permissionService.deletePermission(id);
    }
}
