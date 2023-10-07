package be.ucll.ip.reeks562.storage.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Page<Storage> findStoragesByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Storage> findAllByOrderByNameAsc(Pageable pageable);
    Page<Storage> findAllByOrderByHeightDesc(Pageable pageable);
    Page<Storage> findAll(Pageable pageable);
}
