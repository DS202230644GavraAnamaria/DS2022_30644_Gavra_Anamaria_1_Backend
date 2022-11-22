package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, UUID> {


    /**
     * Example: JPA generate Query by Field
     */
    List<User> findByValue(float value);

    /**
     * Example: Write Custom Query
     */
    @Query(value = "SELECT p " +
            "FROM Consumption p " +
            "WHERE p.value = :value ")
    Optional<User> findConsByValue(@Param("value") float value);
}
