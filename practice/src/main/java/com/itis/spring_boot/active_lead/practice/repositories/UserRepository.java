package com.itis.spring_boot.active_lead.practice.repositories;

import com.itis.spring_boot.active_lead.practice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String s);
}
