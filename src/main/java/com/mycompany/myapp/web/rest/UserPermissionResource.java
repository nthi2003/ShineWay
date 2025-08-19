package com.mycompany.myapp.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.service.UserPermissionService;
import com.mycompany.myapp.service.dto.CreateUserPmDTO;
import com.mycompany.myapp.service.dto.UserPermissionDTO;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api")
public class UserPermissionResource {
    private final UserPermissionService userPermissionService;

    public UserPermissionResource(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }
    @PostMapping("/userPermission")
    @PreAuthorize("hasRole('ADMIN')")
    public UserPermissionDTO createUserPermission(@RequestBody CreateUserPmDTO createUserPmDTO) {
        return userPermissionService.createUserPermission(createUserPmDTO);
    }
    @GetMapping("/userPermission")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserPermissionDTO> getAllUserPermissions() {
        return userPermissionService.getAll();
    }
    @PutMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserPermissionDTO updateUserPermission(@PathVariable String id, @RequestBody UserPermissionDTO userPermissionDTO) {

        return userPermissionService.updateUserPermission(id, userPermissionDTO);
    }
    @DeleteMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserPermission(@PathVariable String id) {
        userPermissionService.deleteUserPermission(id);
    }
    @GetMapping("/userPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserPermissionDTO getUserPermission(@PathVariable String id) {
        return userPermissionService.getUserPermission(id);
    }

}
