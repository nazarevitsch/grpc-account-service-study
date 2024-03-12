package com.study.grpc.account.service.converter;

import com.study.grpc.account.client.Person;
import com.study.grpc.account.client.Persons;
import com.study.grpc.account.client.Pet;
import com.study.grpc.account.service.model.DbPerson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonConverter {

    public Person fromDbToDto(DbPerson person, List<Pet> pets) {
        Person.Builder builder = fromDbToDtoBuilder(person);
        builder.addAllPets(pets);
        return builder.build();
    }

    public Person fromDbToDto(DbPerson person) {
        Person.Builder builder = fromDbToDtoBuilder(person);
        return builder.build();
    }

    public Persons fromDbsToDtos(List<DbPerson> persons) {
        return Persons.newBuilder()
                .addAllPersons(persons.stream()
                        .map(this::fromDbToDto)
                        .toList())
                .build();
    }

    public Person.Builder fromDbToDtoBuilder(DbPerson person) {
        Person.Builder builder = Person.newBuilder();
        Optional.ofNullable(person.getId()).ifPresent(builder::setId);
        Optional.ofNullable(person.getFirstName()).ifPresent(builder::setFirstName);
        Optional.ofNullable(person.getLastName()).ifPresent(builder::setLastName);
        Optional.ofNullable(person.getEmail()).ifPresent(builder::setEmail);
        Optional.ofNullable(person.getAge()).ifPresent(builder::setAge);
        return builder;
    }

    public DbPerson fromDtoToDb(Person dto) {
        DbPerson person = new DbPerson();
        if (dto.hasId()) person.setId(dto.getId());
        if (dto.hasFirstName()) person.setFirstName(dto.getFirstName());
        if (dto.hasLastName()) person.setLastName(dto.getLastName());
        if (dto.hasEmail()) person.setEmail(dto.getEmail());
        if (dto.hasAge()) person.setAge(dto.getAge());
        return person;
    }
}
