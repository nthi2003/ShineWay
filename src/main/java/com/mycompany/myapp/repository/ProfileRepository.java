package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Profile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID>, JpaSpecificationExecutor<Profile> {
    default Optional<Profile> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Profile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Profile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select profile from Profile profile left join fetch profile.user",
        countQuery = "select count(profile) from Profile profile"
    )
    Page<Profile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select profile from Profile profile left join fetch profile.user")
    List<Profile> findAllWithToOneRelationships();

    @Query("select profile from Profile profile left join fetch profile.user where profile.id =:id")
    Optional<Profile> findOneWithToOneRelationships(@Param("id") UUID id);
}
