package com.tomek.repository;

import com.tomek.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    public Player findDistinctById(Long id);
}
