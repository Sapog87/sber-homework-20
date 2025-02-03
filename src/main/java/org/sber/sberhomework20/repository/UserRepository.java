package org.sber.sberhomework20.repository;

import org.sber.sberhomework20.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);
}
