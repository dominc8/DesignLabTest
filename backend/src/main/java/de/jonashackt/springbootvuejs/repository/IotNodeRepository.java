package de.jonashackt.springbootvuejs.repository;


import de.jonashackt.springbootvuejs.entity.IotNode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IotNodeRepository extends CrudRepository<IotNode, Long> {

    List<IotNode> findAllByOrderByIdAsc();
}

