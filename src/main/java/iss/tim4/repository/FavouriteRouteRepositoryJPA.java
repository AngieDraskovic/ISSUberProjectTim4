package iss.tim4.repository;

import iss.tim4.domain.model.FavouriteRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FavouriteRouteRepositoryJPA extends JpaRepository<FavouriteRoute, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM FavouriteRoute fr WHERE fr.id IN (SELECT fr.id FROM fr JOIN fr.passengers p JOIN fr.locations r JOIN r.startLocation sl JOIN r.endLocation el WHERE p.id = :passengerId AND sl.address = :startAddress AND el.address = :endAddress)")
    void removeRouteFromFavourites(@Param("passengerId") Integer passengerId, @Param("startAddress") String startAddress, @Param("endAddress") String endAddress);
}
