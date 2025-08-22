package com.mycompany.myapp.security;

import com.mycompany.myapp.repository.UserPermissionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionEvaluator {

    private final UserPermissionRepository authorityPermissionRepository;

    public PermissionEvaluator(UserPermissionRepository authorityPermissionRepository) {
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

        // Lấy userId hiện tại
        Long userId = SecurityUtils.getCurrentUserId().orElse(null);
        if (userId == null) {
            return false;
        }

        // Kiểm tra quyền trong cơ sở dữ liệu
        return authorityPermissionRepository.existsByUserIdAndPermissionCode(userId, requiredPermission);
    }
}
