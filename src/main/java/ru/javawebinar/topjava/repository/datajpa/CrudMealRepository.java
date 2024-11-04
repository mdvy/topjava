package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("FROM Meal m WHERE m.id = :id AND m.user.id = :userId")
    Optional<Meal> getByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    @Query("FROM Meal m WHERE m.user.id = :userId ORDER BY m.dateTime DESC")
    List<Meal> getAllByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id = :id AND m.user.id = :userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("FROM Meal m WHERE m.user.id = :userId AND m.dateTime >= :start AND m.dateTime < :end ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("start") LocalDateTime startDateTime, @Param("end") LocalDateTime endDateTime, @Param("userId") int userId);

    @Query("FROM Meal m JOIN FETCH m.user WHERE m.id = :id AND m.user.id = :userId")
    Meal getByIdAndUserIdWithUser(@Param("id") int id, @Param("userId") int userId);
}