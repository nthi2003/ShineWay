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
    
    @Query("SELECT c FROM Profile c WHERE c.isDeleted = false")
    default List<Profile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }
    
    @Query("SELECT c FROM Profile c WHERE c.isDeleted = false")
    default Page<Profile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select profile from Profile profile left join fetch profile.user where profile.isDeleted = false",
        countQuery = "select count(profile) from Profile profile where profile.isDeleted = false"
    )
    Page<Profile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select profile from Profile profile left join fetch profile.user where profile.isDeleted = false")
    List<Profile> findAllWithToOneRelationships();

    @Query("select profile from Profile profile left join fetch profile.user where profile.id =:id and profile.isDeleted = false")
    Optional<Profile> findOneWithToOneRelationships(@Param("id") UUID id);

    

    Optional<Profile> findByIdAndIsDeletedFalse(UUID id);


}
