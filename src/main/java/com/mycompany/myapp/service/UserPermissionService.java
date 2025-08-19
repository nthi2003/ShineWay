package com.mycompany.myapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.UserPermission;
import com.mycompany.myapp.domain.Permission;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserPermissionRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.repository.PermissionRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.CreateUserPmDTO;
import com.mycompany.myapp.service.dto.UserPermissionDTO;

import com.mycompany.myapp.service.mapper.UserPermissionMapper;

@Service
@Transactional
public class UserPermissionService {
    private final UserPermissionRepository userPermissionRepository;
    private final UserPermissionMapper userPermissionMapper;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public UserPermissionService(
        UserPermissionRepository userPermissionRepository,
        UserPermissionMapper userPermissionMapper,
        UserRepository userRepository,
        PermissionRepository permissionRepository
    ) {
        this.userPermissionRepository = userPermissionRepository;
        this.userPermissionMapper = userPermissionMapper;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public UserPermissionDTO createUserPermission(CreateUserPmDTO createDTO) {
        UserPermission userPermission = new UserPermission();

        User user = userRepository.findById(createDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + createDTO.getUserId()));

        Permission permission = permissionRepository.findById(createDTO.getPermissionId())
            .orElseThrow(() -> new RuntimeException("Permission not found with id: " + createDTO.getPermissionId()));

        userPermission.setUser(user);
        userPermission.setPermission(permission);

        Long currentUserId = SecurityUtils.getCurrentUserId()
            .orElseThrow(() -> new RuntimeException("Not logged in"));
        userPermission.setCreatedBy(currentUserId);
        userPermission.setCreatedDate(LocalDateTime.now());
        userPermission.setIsDeleted(false);

        userPermission = userPermissionRepository.save(userPermission);
        return userPermissionMapper.userPermissionToDTO(userPermission);
    }
    public List<UserPermissionDTO> getAll () {
        return userPermissionMapper.userPermissionToDTOs(userPermissionRepository.findAll());
    }
    public UserPermissionDTO getUserPermission(String id) {
        return userPermissionRepository.findById(id)
            .map(userPermissionMapper::userPermissionToDTO)
            .orElseThrow(() -> new RuntimeException("UserPermission not found with id: " + id));
    }
    public UserPermissionDTO updateUserPermission (String id, UserPermissionDTO userPermissionDTO) {
        UserPermission userPermission = userPermissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPermission not found with id: " + id));
        userPermission.setUser(userRepository.findById(userPermissionDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userPermissionDTO.getUserId())));
        userPermission.setPermission(permissionRepository.findById(userPermissionDTO.getPermissionId())
            .orElseThrow(() -> new RuntimeException("Permission not found with id: " + userPermissionDTO.getPermissionId())));
        userPermission.setUpdatedBy(SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in")));
        userPermission.setUpdatedDate(LocalDateTime.now());
        userPermission = userPermissionRepository.save(userPermission);
        return userPermissionMapper.userPermissionToDTO(userPermission);
    }
public void deleteUserPermission(String id) {
      userPermissionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("UserPermission not found with id: " + id));

    userPermissionRepository.deleteById(id);
}
}