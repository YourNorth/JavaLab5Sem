package com.itis.spring_boot.active_lead.practice.repositories;

import com.itis.spring_boot.active_lead.practice.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
