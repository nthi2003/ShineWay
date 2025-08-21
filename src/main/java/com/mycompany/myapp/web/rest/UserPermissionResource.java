package com.mycompany.myapp.web.rest;

import org.springframework.web.bind.annotation.*;
import com.mycompany.myapp.service.UserPermissionService;
import com.mycompany.myapp.service.UserPermissionQueryService; // Thêm dòng này
import com.mycompany.myapp.service.criteria.UserPermissionCriteria; // Thêm dòng này
import com.mycompany.myapp.service.dto.CreateUserPmDTO;
import com.mycompany.myapp.service.dto.UserPermissionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
public class UserPermissionResource {
    private final UserPermissionService userPermissionService;
    private final UserPermissionQueryService userPermissionQueryService;

    public UserPermissionResource(
        UserPermissionService userPermissionService,
        UserPermissionQueryService userPermissionQueryService
    ) {
        this.userPermissionService = userPermissionService;
        this.userPermissionQueryService = userPermissionQueryService; 
    }

    @PostMapping("/userPermission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserPermissionDTO createUserPermission(@RequestBody CreateUserPmDTO createUserPmDTO) {
        return userPermissionService.createUserPermission(createUserPmDTO);
    }


    @GetMapping("/userPermission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserPermissionDTO>> getAllUserPermissions(
        UserPermissionCriteria criteria, Pageable pageable
    ) {
        Page<UserPermissionDTO> userPermissions = userPermissionQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(userPermissions);
    }

    @PutMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserPermissionDTO updateUserPermission(@PathVariable String id, @RequestBody UserPermissionDTO userPermissionDTO) {
        return userPermissionService.updateUserPermission(id, userPermissionDTO);
    }

    @DeleteMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserPermission(@PathVariable String id) {
        userPermissionService.deleteUserPermission(id);
    }

    @GetMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserPermissionDTO getUserPermission(@PathVariable String id) {
        return userPermissionService.getUserPermission(id);
    }   
}