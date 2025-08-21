package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserPermissionRepository extends JpaRepository<UserPermission, String>, JpaSpecificationExecutor<UserPermission> {
    // /**
    //  * Kiểm tra xem có tồn tại bản ghi nào trong bảng authority_permission
    //  * mà authority_name nằm trong danh sách authorityNames và permission_code đúng bằng permissionCode hay không.
    //  *
    //  * @param authorityNames danh sách tên role (authority) của user
    //  * @param permissionCode mã của permission cần kiểm tra
    //  * @return true nếu có ít nhất một role trong authorityNames được gán permission này, ngược lại trả về false
    //  */
    @Query(
        """
            SELECT COUNT(ap) > 0
            FROM UserPermission ap
            JOIN ap.permission p
            WHERE ap.user.id = :userId AND p.code = :permissionCode
        """
    )
    boolean existsByUserIdAndPermissionCode(Long userId, String permissionCode);
}
