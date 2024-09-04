package com.pagamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagamento.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
