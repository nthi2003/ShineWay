package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Permission;
import com.mycompany.myapp.repository.PermissionRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.PermissionDTO;
import com.mycompany.myapp.service.mapper.PermissionMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {

    private final PermissionRepository permissionRepository;
    public final PermissionMapper permissionMapper;

    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = permissionMapper.permissionDTOToPermission(permissionDTO);
        Long currentUserId = SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in"));
        permission.setCreatedBy(currentUserId);
        permission = permissionRepository.save(permission);
        return permissionMapper.permissionToPermissionDTO(permission);
    }

    public PermissionDTO getPermission(String id) {
        return permissionRepository
            .findByIdAndIsDeletedFalse(id)
            .map(permissionMapper::permissionToPermissionDTO)
            .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    public List<PermissionDTO> getAllPermissions() {
        return permissionMapper.permissionToPermissionDTOs(permissionRepository.findAllByIsDeletedFalse());
    }

    public PermissionDTO updatePermission(PermissionDTO permissionDTO) {
        Permission permission = permissionRepository
            .findById(permissionDTO.getId())
            .orElseThrow(() -> new RuntimeException("Permission not found"));
        if (permission.getIsDeleted()) {
            throw new RuntimeException("Permission is deleted");
        }
        permission.setCode(permissionDTO.getCode());
        permission.setResource(permissionDTO.getResource());
        permission.setDescription(permissionDTO.getDescription());
        permission.setUpdatedBy(SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in")));
        permission.setUpdatedDate(LocalDateTime.now());
        permission.setIsDeleted(permission.getIsDeleted());
        permission = permissionRepository.save(permission);
        return permissionMapper.permissionToPermissionDTO(permission);
    }

    public void deletePermission(String id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission not found"));
        permission.setIsDeleted(true);
        permissionRepository.save(permission);
    }
}
