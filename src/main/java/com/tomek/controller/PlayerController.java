package com.tomek.controller;

import com.tomek.model.Player;
import com.tomek.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable Long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> savePlayer(@RequestBody Player player) {
        if (player.getId() == null) {
            Player savedPlayer = playerRepository.save(player);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(player.getId())
                    .toUri();
            return ResponseEntity.created(location).body(savedPlayer);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}