package com.mycompany.myapp.security;

import com.mycompany.myapp.repository.AuthorityPermissionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionEvaluator {

    private final AuthorityPermissionRepository authorityPermissionRepository;

    public PermissionEvaluator(AuthorityPermissionRepository authorityPermissionRepository) {
        this.authorityPermissionRepository = authorityPermissionRepository;
    }

    /**
     * Kiểm tra xem người dùng hiện tại có quyền cần thiết hay không.
     * @param requiredPermission Mã quyền cần kiểm tra.
     * @return true nếu người dùng có quyền, false nếu không.
     */
    public boolean hasAuthority(String requiredPermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Lấy danh sách quyền của người dùng hiện tại
        Iterable<String> authorityNames = authentication.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .toList();

        // Kiểm tra quyền trong cơ sở dữ liệu
        return authorityPermissionRepository.existsByAuthorityNameInAndPermissionCode(authorityNames, requiredPermission);
    }
}