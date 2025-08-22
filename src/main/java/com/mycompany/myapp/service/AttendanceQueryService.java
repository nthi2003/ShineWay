package com.mycompany.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mycompany.myapp.domain.Attendance;
import com.mycompany.myapp.domain.Attendance_;
import com.mycompany.myapp.domain.User_;
import com.mycompany.myapp.repository.AttendanceRepository;
import com.mycompany.myapp.service.criteria.AttendanceCriteria;
import com.mycompany.myapp.service.dto.AttendanceDTO;
import com.mycompany.myapp.service.mapper.AttendanceMapper;

import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional
public class AttendanceQueryService extends QueryService<Attendance> {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceQueryService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findByCriteria(AttendanceCriteria criteria, Pageable pageable) {
        final Specification<Attendance> specification = createSpecification(criteria)
            .and((root, query, cb) -> cb.isFalse(root.get("isDeleted")));
        return attendanceRepository.findAll(specification, pageable)
            .map(attendanceMapper::attendanceToAttendanceDTO);
    }

    protected Specification<Attendance> createSpecification(AttendanceCriteria criteria) {
        Specification<Attendance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Attendance_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getUserId(), root -> root.join(Attendance_.user).get(User_.id))
                );
            }
            if (criteria.getCheckIn() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckIn(), Attendance_.checkIn));
            }
            if (criteria.getCheckOut() != null) {
                specification = specification.and(buildSpecification(criteria.getCheckOut(), Attendance_.checkOut));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildSpecification(criteria.getDay(), Attendance_.day));
            }
        }
        return specification;
    }
}