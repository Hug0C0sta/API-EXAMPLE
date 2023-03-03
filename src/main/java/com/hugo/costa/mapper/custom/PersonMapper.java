package com.hugo.costa.mapper.custom;

import com.hugo.costa.data.vo.v2.PersonVOV2;
import com.hugo.costa.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setAddress(person.getAddress());
        vo.setFirstName(person.getFirstName());
        vo.setBirthDay(new Date());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());

        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setAddress(person.getAddress());
        entity.setFirstName(person.getFirstName());
        //vo.setBirthDay(new Date());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());

        return entity;
    }
}
