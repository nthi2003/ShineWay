package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Permission;
import com.mycompany.myapp.service.dto.PermissionDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PermissionMapper {

    // Chuyển đổi từ danh sách Permission sang danh sách PermissionDTO
    public List<PermissionDTO> permissionToPermissionDTOs(List<Permission> permissions) {
        return permissions.stream().map(this::permissionToPermissionDTO).collect(Collectors.toList());
    }

    // Chuyển đổi từ Permission sang PermissionDTO
    public PermissionDTO permissionToPermissionDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setCode(permission.getCode());
        dto.setResource(permission.getResource());
        dto.setDescription(permission.getDescription());
        dto.setIsDeleted(permission.getIsDeleted());
        dto.setCreatedBy(permission.getCreatedBy());
        dto.setUpdatedBy(permission.getUpdatedBy());
        dto.setCreatedDate(permission.getCreatedDate());
        dto.setUpdatedDate(permission.getUpdatedDate());
        return dto;
    }

    // Chuyển đổi từ danh sách PermissionDTO sang danh sách Permission
    public List<Permission> permissionDTOToPermissions(List<PermissionDTO> dtos) {
        return dtos.stream().filter(Objects::nonNull).map(this::permissionDTOToPermission).collect(Collectors.toList());
    }

    // Chuyển đổi từ PermissionDTO sang Permission
    public Permission permissionDTOToPermission(PermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setCode(dto.getCode());
        permission.setResource(dto.getResource());
        permission.setDescription(dto.getDescription());
        permission.setIsDeleted(false);
        permission.setCreatedBy(null); // Sẽ được set ở Service
        permission.setUpdatedBy(dto.getUpdatedBy());
        permission.setCreatedDate(java.time.LocalDateTime.now());
        permission.setUpdatedDate(dto.getUpdatedDate());
        return permission;
    }

    public Permission permissionFromId(String id) {
        if (id == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }
}
