package be.ucll.ip.reeks562.boat.domain;

import be.ucll.ip.reeks562.storage.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {
    Boat findBoatByAssuranceNumber(String assuranceNumber);

    List<Boat> findBoatByHeightAndWidth(double height, double width);

    List<Boat> findBoatByStorage(Storage storage);
}