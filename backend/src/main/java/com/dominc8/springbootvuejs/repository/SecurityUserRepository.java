package com.dominc8.springbootvuejs.repository;


import com.dominc8.springbootvuejs.entity.SecurityUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SecurityUserRepository extends CrudRepository<SecurityUser, Long> {

    boolean existsByUsername(String username);

    List<SecurityUser> findAllByOrderByIdAsc();

}
