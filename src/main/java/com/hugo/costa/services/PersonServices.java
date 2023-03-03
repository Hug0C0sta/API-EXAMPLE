package com.hugo.costa.services;

import com.hugo.costa.controllers.PersonController;
import com.hugo.costa.data.vo.v1.PersonVO;
import com.hugo.costa.data.vo.v2.PersonVOV2;
import com.hugo.costa.exceptions.RequiredObjectsIsNullException;
import com.hugo.costa.exceptions.ResourceNotFoundException;
import com.hugo.costa.mapper.DozerMapper;
import com.hugo.costa.mapper.custom.PersonMapper;
import com.hugo.costa.model.Person;
import com.hugo.costa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.logging.Logger;
@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public PersonVO findById(Long id) throws Exception {
        logger.info("Finding one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));;
            var vo = DozerMapper.parseObject(entity, PersonVO.class);
            vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
            return vo;
    }

    public List<PersonVO> findAll() throws Exception {
        logger.info("Finding all people!");
        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        for (PersonVO p : persons) {
            p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
        }
        return persons;
    }


    public PersonVO create(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectsIsNullException();
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV0V2(PersonVOV2 person){
        logger.info("Creating one person with V2!");
        var entity = mapper.convertVoToEntity(person);
        var vo =  mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectsIsNullException();
        logger.info("Creating one person!");
        Person entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }

}
