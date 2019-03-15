package com.tomek.controller;

import com.tomek.model.Player;
import com.tomek.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    //set return type as JSON for GET request
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //return list of players as response entity
    public ResponseEntity<List<Player>> allPlayers() {
        // select all players from repository and add to the list
        List<Player> allPlayers = playerRepository.findAll();
        // return http status 200 (ok) and all players list as JSON
        return ResponseEntity.ok(allPlayers);
    }

    // set return notation type as JSON
    // id is variable added to url for example /api/players/2 returns 3rd player's data form list
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // @PathVariable id = {id} form above. If path variable name = method variable name same method args can look as below
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerRepository.findDistinctById(id);
        // return http status 200 (ok) and player data with id from url
        return ResponseEntity.ok(player);
    }

    // set consume notation type as JSON
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    // @RequestBody means that object is passed to the method from request
    public ResponseEntity<?> savePlayer(@RequestBody Player player) {
        // save player to the repository
        Player save = playerRepository.save(player);
        // set location of newly saved object
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();
        // in response send location of created object and object
        return ResponseEntity.created(location).body(save);
    }
}
