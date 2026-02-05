package com.demo.mongodb.service;

import com.demo.mongodb.dao.RoleRepository;
import com.demo.mongodb.dao.UserRepository;
import com.demo.mongodb.dto.UserRequestDTO;
import com.demo.mongodb.dto.UserResponseDTO;
import com.demo.mongodb.entity.Role;
import com.demo.mongodb.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  public UserResponseDTO create(UserRequestDTO dto) {

    Role role = roleRepository.findByName(dto.getRoleName())
        .orElseGet(() -> {
          Role newRole = new Role();
          newRole.setName(dto.getRoleName());
          return roleRepository.save(newRole);
        });

    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setAge(dto.getAge());
    user.setRole(role);

    User saved = userRepository.save(user);

    return new UserResponseDTO(
        saved.getId(),
        saved.getName(),
        saved.getEmail(),
        saved.getAge(),
        saved.getRole().getName(),
        saved.getCreatedAt(),
        saved.getUpdatedAt()
    );

  }


  public UserResponseDTO getById(String id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return new UserResponseDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getAge(),
        user.getRole().getName(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public Page<UserResponseDTO> getAll(int page, int size) {
    return userRepository.findAll(PageRequest.of(page, size))
        .map(user -> new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAge(),
            user.getRole().getName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        ));
  }

  public UserResponseDTO update(String id, UserRequestDTO dto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setAge(dto.getAge());

    User updated = userRepository.save(user);

    return new UserResponseDTO(
        updated.getId(),
        updated.getName(),
        updated.getEmail(),
        updated.getAge(),
        updated.getRole().getName(),
        updated.getCreatedAt(),
        updated.getUpdatedAt()
    );
  }

  public void delete(String id) {
    userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    userRepository.deleteById(id);
  }
}
