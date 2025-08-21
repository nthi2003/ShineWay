package com.mycompany.myapp.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Permission;
import com.mycompany.myapp.domain.Permission_;
import com.mycompany.myapp.repository.PermissionRepository;
import com.mycompany.myapp.service.criteria.PermissionCriteria;
import com.mycompany.myapp.service.dto.PermissionDTO;
import com.mycompany.myapp.service.mapper.PermissionMapper;

import tech.jhipster.service.QueryService;
@Service
@Transactional
public class PermissionQueryService extends QueryService<Permission> {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionQueryService(PermissionRepository permissionRepository , PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;   
    }

    @Transactional(readOnly = true)
    public Page<PermissionDTO> findByCriteria(PermissionCriteria criteria, Pageable pageable) {
        final Specification<Permission> specification = createSpecification(criteria)
            .and((root, query, cb) -> cb.isFalse(root.get("isDeleted")));
        return permissionRepository.findAll(specification, pageable)
            .map(permissionMapper::permissionToPermissionDTO);
    }
    protected Specification<Permission> createSpecification(PermissionCriteria criteria) {
        Specification<Permission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Permission_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Permission_.code));
            }
            if (criteria.getResource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResource(), Permission_.resource));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Permission_.description));
            }
        }
        return specification;
    }
}
