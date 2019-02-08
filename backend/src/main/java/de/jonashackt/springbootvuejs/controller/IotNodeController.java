package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.entity.IotNode;
import de.jonashackt.springbootvuejs.repository.IotNodeRepository;
import de.jonashackt.springbootvuejs.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class IotNodeController {

    @Autowired
    private IotNodeRepository iotNodeRepository;

    @RequestMapping(method = RequestMethod.GET, value = {"", "/"})
    public List<IotNode> getAllIotNodes() {
        return iotNodeRepository.findAllByOrderByIdAsc();
    }


    @RequestMapping(method = RequestMethod.POST, value = {"", "/"})
    public void addIotNode(@RequestBody IotNode iotNode) {
        iotNodeRepository.save(iotNode);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public IotNode getIotNode(@PathVariable("id") long id) {
        return iotNodeRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public void updateIotNode(@PathVariable("id") long id, @RequestBody IotNode updatedIotNode){
        IotNode iotNode = iotNodeRepository.findById(id).orElseThrow(() -> new RuntimeException());

        if (updatedIotNode.getDuty1() != null) iotNode.setDuty1(updatedIotNode.getDuty1());
        if (updatedIotNode.getDuty2() != null) iotNode.setDuty2(updatedIotNode.getDuty2());
        if (updatedIotNode.getDuty3() != null) iotNode.setDuty3(updatedIotNode.getDuty3());
        if (updatedIotNode.getMessage() != null) iotNode.setMessage(updatedIotNode.getMessage());

        iotNodeRepository.save(iotNode);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteIotNode(@PathVariable("id") long id) {
        iotNodeRepository.deleteById(id);
    }

}
