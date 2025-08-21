package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Tables;
import com.mycompany.myapp.domain.Tables_;
import com.mycompany.myapp.repository.TablesRepository;
import com.mycompany.myapp.service.criteria.TableCriteria;
import com.mycompany.myapp.service.dto.TableDTO;
import com.mycompany.myapp.service.mapper.TableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional
public class TableQueryService extends QueryService<Tables> {

    private final TablesRepository tableRepository;
    private final TableMapper tableMapper;

    public TableQueryService(TablesRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    @Transactional(readOnly = true)
    public Page<TableDTO> findByCriteria(TableCriteria criteria, Pageable pageable) {
        final Specification<Tables> specification = createSpecification(criteria).and((root, query, cb) -> cb.isFalse(root.get("isDeleted"))
        );

        return tableRepository.findAll(specification, pageable).map(tableMapper::tableToTableDTO);
    }

    protected Specification<Tables> createSpecification(TableCriteria criteria) {
        Specification<Tables> specification = Specification.where(null);
        if (criteria.getId() != null) {
            specification = specification.and(buildSpecification(criteria.getId(), Tables_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), Tables_.name));
        }
        if (criteria.getCapacity() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getCapacity(), Tables_.capacity));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getStatus(), Tables_.status));
        }
        return specification;
    }
}
