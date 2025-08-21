package com.mycompany.myapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.UserPermission;
import com.mycompany.myapp.domain.UserPermission_;
import com.mycompany.myapp.domain.User_;
import com.mycompany.myapp.domain.Permission_;
import com.mycompany.myapp.repository.UserPermissionRepository;
import com.mycompany.myapp.service.criteria.UserPermissionCriteria;
import com.mycompany.myapp.service.dto.UserPermissionDTO;
import com.mycompany.myapp.service.mapper.UserPermissionMapper;

import tech.jhipster.service.QueryService;

@Service
@Transactional
public class UserPermissionQueryService extends QueryService<UserPermission> {
    private final UserPermissionRepository userPermissionRepository;
    private final UserPermissionMapper userPermissionMapper;

    public UserPermissionQueryService(UserPermissionRepository userPermissionRepository, UserPermissionMapper userPermissionMapper) {
        this.userPermissionRepository = userPermissionRepository;
        this.userPermissionMapper = userPermissionMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserPermissionDTO> findByCriteria(UserPermissionCriteria criteria, Pageable pageable) {
        final Specification<UserPermission> specification = createSpecification(criteria)
            .and((root, query, cb) -> cb.isFalse(root.get("isDeleted")));

        return userPermissionRepository.findAll(specification, pageable)
            .map(userPermissionMapper::userPermissionToDTO);
    }

    protected Specification<UserPermission> createSpecification(UserPermissionCriteria criteria) {
        Specification<UserPermission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserPermission_.id));
            }
            if (criteria.getUserId() != null) {
          
                specification = specification.and(
                    buildReferringEntitySpecification(criteria.getUserId(), UserPermission_.user, User_.id)
                );
            }
            if (criteria.getPermissionId() != null) {
                specification = specification.and(
                    buildReferringEntitySpecification(criteria.getPermissionId(), UserPermission_.permission, Permission_.id)
                );
            }
        }
        return specification;
    }
}