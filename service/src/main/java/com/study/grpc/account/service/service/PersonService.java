package com.study.grpc.account.service.service;

import com.study.grpc.account.client.*;
import com.study.grpc.account.service.converter.PersonConverter;
import com.study.grpc.account.service.converter.PetConverter;
import com.study.grpc.account.service.dao.PersonRepository;
import com.study.grpc.account.service.dao.PetRepository;
import com.study.grpc.account.service.model.DbPerson;
import com.study.grpc.account.service.model.DbPet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PetRepository petRepository;
    private final PersonConverter personConverter;
    private final PetConverter petConverter;

    public Person create(Person dto) {
        DbPerson person = this.personConverter.fromDtoToDb(dto);
        DbPerson savedPerson = this.personRepository.save(person);
        List<DbPet> pets = dto.getPetsList()
                .stream()
                .map(this.petConverter::fromDtoToDb)
                .peek(pet -> pet.setOwnerId(savedPerson.getId()))
                .toList();
        List<Pet> savedPets = this.petRepository.saveAll(pets)
                .stream()
                .map(this.petConverter::fromDbToDto)
                .toList();
        return this.personConverter.fromDbToDto(savedPerson, savedPets);
    }

    public Person get(Long id) {
        DbPerson person = this.personRepository.findById(id).get();
        List<Pet> pets = this.petRepository.findAllByOwnerId(id)
                .stream()
                .map(this.petConverter::fromDbToDto)
                .toList();
        return this.personConverter.fromDbToDto(person, pets);
    }

    public Persons getByIds(List<Long> ids) {
        List<DbPerson> persons = this.personRepository.findAllById(ids);
        return this.personConverter.fromDbsToDtos(persons);
    }

    public Persons getAll() {
        List<DbPerson> persons = this.personRepository.findAll();
        return this.personConverter.fromDbsToDtos(persons);
    }

    public Persons getIdsIsNotInScope(List<Long> ids) {
        List<DbPerson> persons = this.personRepository.findAllByIdNotIn(ids);
        return this.personConverter.fromDbsToDtos(persons);
    }

    public void delete(Long id) {
        this.personRepository.deleteById(id);
    }
}
