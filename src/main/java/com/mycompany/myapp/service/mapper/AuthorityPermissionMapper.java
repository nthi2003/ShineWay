package com.mycompany.myapp.service.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.AuthorityPermission;
import com.mycompany.myapp.service.dto.AuthorityPermissionDTO;

@Service
public class AuthorityPermissionMapper {

    public List<AuthorityPermissionDTO> authorityPermissionToAuthorityPermissionDTOs(List<AuthorityPermission> authorityPermissions) {
        return authorityPermissions.stream()
            .filter(Objects::nonNull)
            .map(this::authorityPermissionToDTO)
            .toList();
    }

    public AuthorityPermissionDTO authorityPermissionToDTO(AuthorityPermission authorityPermission) {
        if (authorityPermission == null) {
            return null;
        }
        AuthorityPermissionDTO dto = new AuthorityPermissionDTO();
        dto.setId(authorityPermission.getId());
        dto.setAuthorityName(authorityPermission.getAuthority().getName());
        dto.setPermissionId(authorityPermission.getPermission().getId());
        dto.setIsDeleted(authorityPermission.getIsDeleted());
        dto.setCreatedBy(authorityPermission.getCreatedBy());
        dto.setUpdatedBy(authorityPermission.getUpdatedBy());
        dto.setCreatedDate(authorityPermission.getCreatedDate());
        dto.setUpdatedDate(authorityPermission.getUpdatedDate());
        return dto;
    }

    public List<AuthorityPermission> authorityPermissionDTOsToEntities(List<AuthorityPermissionDTO> dtos) {
        return dtos.stream()
            .filter(Objects::nonNull)
            .map(this::authorityPermissionDTOToEntity)
            .toList();
    }

    public AuthorityPermission authorityPermissionDTOToEntity(AuthorityPermissionDTO dto) {
        if (dto == null) {
            return null;
        }
        AuthorityPermission authorityPermission = new AuthorityPermission();
        authorityPermission.setId(dto.getId()); // Thêm setId
        authorityPermission.setAuthority(null);
        authorityPermission.setPermission(null);
        authorityPermission.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false); // Sử dụng giá trị từ DTO nếu có
        authorityPermission.setCreatedBy(dto.getCreatedBy());
        authorityPermission.setUpdatedBy(dto.getUpdatedBy());
        authorityPermission.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : java.time.LocalDateTime.now());
        authorityPermission.setUpdatedDate(dto.getUpdatedDate());
        return authorityPermission;
    }
}