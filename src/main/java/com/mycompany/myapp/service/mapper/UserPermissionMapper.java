package com.mycompany.myapp.service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.UserPermission;
import com.mycompany.myapp.service.dto.UserPermissionDTO;

@Service
public class UserPermissionMapper {

    public List<UserPermissionDTO> userPermissionToDTOs(List<UserPermission> userPermissions) {
        return userPermissions.stream()
            .filter(Objects::nonNull)
            .map(this::userPermissionToDTO)
            .toList();
    }

    public UserPermissionDTO userPermissionToDTO(UserPermission userPermission) {
        if (userPermission == null) {
            return null;
        }
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setId(userPermission.getId());
        dto.setUserId(userPermission.getUser().getId());
        dto.setPermissionId(userPermission.getPermission().getId());
        dto.setIsDeleted(userPermission.getIsDeleted());
        dto.setCreatedBy(userPermission.getCreatedBy());
        dto.setUpdatedBy(userPermission.getUpdatedBy());
        dto.setCreatedDate(userPermission.getCreatedDate());
        dto.setUpdatedDate(userPermission.getUpdatedDate());
        return dto;
    }

    public List<UserPermission> userPermissionDTOsToEntities(List<UserPermissionDTO> dtos) {
        return dtos.stream()
            .filter(Objects::nonNull)
            .map(this::userPermissionDTOToEntity)
            .toList();
    }

    public UserPermission userPermissionDTOToEntity(UserPermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        UserPermission userPermission = new UserPermission();
        userPermission.setId(dto.getId()); // Thêm setId
        userPermission.setUser(null);
        userPermission.setPermission(null);
        userPermission.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false); // Sử dụng giá trị từ DTO nếu có
        userPermission.setCreatedBy(dto.getCreatedBy());
        userPermission.setUpdatedBy(dto.getUpdatedBy());
        userPermission.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : java.time.LocalDateTime.now());
        userPermission.setUpdatedDate(dto.getUpdatedDate());
        return userPermission;
    }
}