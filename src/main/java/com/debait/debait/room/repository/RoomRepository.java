package com.debait.debait.room.repository;

import com.debait.debait.room.entity.Room;
import com.debait.debait.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByUser_Id(String userId);

//    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
//    Optional<Room> findByRoom_id(String roomId);
}
