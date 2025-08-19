package com.mycompany.myapp.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.service.AuthorityPermissionService;
import com.mycompany.myapp.service.dto.AuthorityPermissionDTO;
import com.mycompany.myapp.service.dto.CreateAuthorityPmDTO;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api")
public class AuthorityPermissionResource {
    private final AuthorityPermissionService authorityPermissionService;

    public AuthorityPermissionResource(AuthorityPermissionService authorityPermissionService) {
        this.authorityPermissionService = authorityPermissionService;
    }
    @PostMapping("/authorityPermission")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityPermissionDTO createAuthorityPermission(@RequestBody CreateAuthorityPmDTO createAuthorityPmDTO) {
        return authorityPermissionService.createAuthorityPermission(createAuthorityPmDTO);
    }
    @GetMapping("/authorityPermission")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthorityPermissionDTO> getAllAuthorityPermissions() {
        return authorityPermissionService.getAll();
    }
    @PutMapping("/authorityPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityPermissionDTO updateAuthorityPermission(@PathVariable String id, @RequestBody AuthorityPermissionDTO authorityPermissionDTO) {

        return authorityPermissionService.updateAuthorityPermission(id, authorityPermissionDTO);
    }
    @DeleteMapping("/authorityPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuthorityPermission(@PathVariable String id) {
        authorityPermissionService.deleteAuthorityPermission(id);
    }
    @GetMapping("/authorityPermission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityPermissionDTO getAuthorityPermission(@PathVariable String id) {
        return authorityPermissionService.getAuthorityPermission(id);
    }

}
