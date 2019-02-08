package com.dominc8.springbootvuejs.repository;


import com.dominc8.springbootvuejs.entity.IotNode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IotNodeRepository extends CrudRepository<IotNode, Long> {

    List<IotNode> findAllByOrderByIdAsc();
}

