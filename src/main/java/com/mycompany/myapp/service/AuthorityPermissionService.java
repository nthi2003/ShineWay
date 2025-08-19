package com.mycompany.myapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.AuthorityPermission;
import com.mycompany.myapp.domain.Permission;
import com.mycompany.myapp.repository.AuthorityPermissionRepository;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.repository.PermissionRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.AuthorityPermissionDTO;
import com.mycompany.myapp.service.dto.CreateAuthorityPmDTO;
import com.mycompany.myapp.service.mapper.AuthorityPermissionMapper;

@Service
@Transactional
public class AuthorityPermissionService {
    private final AuthorityPermissionRepository authorityPermissionRepository;
    private final AuthorityPermissionMapper authorityPermissionMapper;
    private final AuthorityRepository authorityRepository; 
    private final PermissionRepository permissionRepository;

    public AuthorityPermissionService(
        AuthorityPermissionRepository authorityPermissionRepository,
        AuthorityPermissionMapper authorityPermissionMapper,
        AuthorityRepository authorityRepository,
        PermissionRepository permissionRepository
    ) {
        this.authorityPermissionRepository = authorityPermissionRepository;
        this.authorityPermissionMapper = authorityPermissionMapper;
        this.authorityRepository = authorityRepository;
        this.permissionRepository = permissionRepository;
    }

    public AuthorityPermissionDTO createAuthorityPermission(CreateAuthorityPmDTO createDTO) {
        AuthorityPermission authorityPermission = new AuthorityPermission();
        
        Authority authority = authorityRepository.findByName(createDTO.getAuthorityName())
            .orElseThrow(() -> new RuntimeException("Authority not found with name: " + createDTO.getAuthorityName()));
        
        Permission permission = permissionRepository.findById(createDTO.getPermissionId())
            .orElseThrow(() -> new RuntimeException("Permission not found with id: " + createDTO.getPermissionId()));
        
        authorityPermission.setAuthority(authority);
        authorityPermission.setPermission(permission);
        
        Long currentUserId = SecurityUtils.getCurrentUserId()
            .orElseThrow(() -> new RuntimeException("Not logged in"));
        authorityPermission.setCreatedBy(currentUserId);
        authorityPermission.setCreatedDate(LocalDateTime.now());
        authorityPermission.setIsDeleted(false);
        
        authorityPermission = authorityPermissionRepository.save(authorityPermission);
        return authorityPermissionMapper.authorityPermissionToDTO(authorityPermission);
    }
    public List<AuthorityPermissionDTO> getAll () {
        return authorityPermissionMapper.authorityPermissionToAuthorityPermissionDTOs(authorityPermissionRepository.findAll());
    }
    public AuthorityPermissionDTO getAuthorityPermission(String id) {
        return authorityPermissionRepository.findById(id)
            .map(authorityPermissionMapper::authorityPermissionToDTO)
            .orElseThrow(() -> new RuntimeException("AuthorityPermission not found with id: " + id));
    }
    public AuthorityPermissionDTO updateAuthorityPermission (String id, AuthorityPermissionDTO authorityPermissionDTO) {
        AuthorityPermission authorityPermission = authorityPermissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AuthorityPermission not found with id: " + id));
        authorityPermission.setAuthority(authorityRepository.findByName(authorityPermissionDTO.getAuthorityName())
            .orElseThrow(() -> new RuntimeException("Authority not found with name: " + authorityPermissionDTO.getAuthorityName())));
        authorityPermission.setPermission(permissionRepository.findById(authorityPermissionDTO.getPermissionId())
            .orElseThrow(() -> new RuntimeException("Permission not found with id: " + authorityPermissionDTO.getPermissionId())));
        authorityPermission.setUpdatedBy(SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in")));
        authorityPermission.setUpdatedDate(LocalDateTime.now());
        authorityPermission = authorityPermissionRepository.save(authorityPermission);
        return authorityPermissionMapper.authorityPermissionToDTO(authorityPermission);
    }
public void deleteAuthorityPermission(String id) {
      authorityPermissionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("AuthorityPermission not found with id: " + id));

    authorityPermissionRepository.deleteById(id);
}
}