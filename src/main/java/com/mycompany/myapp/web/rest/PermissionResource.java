package com.mycompany.myapp.web.rest;

import java.util.List;

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

import com.mycompany.myapp.service.PermissionService;
import com.mycompany.myapp.service.dto.PermissionDTO;

@RestController
@RequestMapping("/api")
public class PermissionResource {
    private final PermissionService permissionService;

    public PermissionResource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO result = permissionService.createPermission(permissionDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionDTO>> getPermission() {
        List<PermissionDTO> result = permissionService.getAllPermissions();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/permission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable String id) {
        PermissionDTO result = permissionService.getPermission(id);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/permission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        permissionDTO.setId(id);
        PermissionDTO result = permissionService.updatePermission(permissionDTO);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/permission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(@PathVariable String id) {
        permissionService.deletePermission(id);
    }
}