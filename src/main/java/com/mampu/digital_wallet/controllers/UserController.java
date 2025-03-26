package com.mampu.digital_wallet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mampu.digital_wallet.helpers.ResponseApi;
import com.mampu.digital_wallet.models.entities.User;
import com.mampu.digital_wallet.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        List<User> user = (List<User>) userService.findAll();

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ResponseApi(false, "User not found", null)
            );
        }
        return ResponseEntity.ok(new ResponseApi(true, "Successfully fetching user", user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        User user = userService.findOne(id);

        if (user == null) {
            return ResponseEntity.status(404).body(
                new ResponseApi(false, "User not found", null)
            );
        }

        return ResponseEntity.ok().body(
            new ResponseApi(true, "Successfully get user", user)
        );
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the given name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully created",
            content = @Content(schema = @Schema(implementation = ResponseApi.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createUser(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Creates a new user with the given name",
                required = true,
                content = @Content(
                        schema = @Schema(example = "{\"name\": \"John Doe\"}")
                )
        )
        @RequestBody User user) {
        User newUser = userService.save(user.getName());
        return ResponseEntity.ok().body(
            new ResponseApi(true, "Successfully create user", newUser)
        );
    }
}
