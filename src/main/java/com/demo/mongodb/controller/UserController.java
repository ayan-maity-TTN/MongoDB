package com.demo.mongodb.controller;

import com.demo.mongodb.dto.UserRequestDTO;
import com.demo.mongodb.dto.UserResponseDTO;
import com.demo.mongodb.entity.User;
import com.demo.mongodb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @PostMapping
  public UserResponseDTO create(@Valid @RequestBody UserRequestDTO dto) {
    return userService.create(dto);
  }

  @GetMapping("/{id}")
  public UserResponseDTO getById(@PathVariable String id) {
    return userService.getById(id);
  }

  @GetMapping
  public Page<UserResponseDTO> getAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    return userService.getAll(page, size);
  }

  @PutMapping("/{id}")
  public UserResponseDTO update(
      @PathVariable String id,
      @Valid @RequestBody UserRequestDTO dto) {
    return userService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    userService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
  }
}

