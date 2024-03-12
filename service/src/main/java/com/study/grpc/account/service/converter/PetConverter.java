package com.study.grpc.account.service.converter;

import com.study.grpc.account.client.Pet;
import com.study.grpc.account.service.model.DbPet;
import org.springframework.stereotype.Component;

@Component
public class PetConverter {

    public Pet fromDbToDto(DbPet pet) {
        return Pet.newBuilder()
                .setId(pet.getId())
                .setName(pet.getName())
                .setOwnerId(pet.getOwnerId())
                .build();
    }

    public DbPet fromDtoToDb(Pet dto) {
        DbPet pet = new DbPet();
        pet.setId(dto.getId());
        pet.setName(dto.getName());
        pet.setOwnerId(dto.getOwnerId());
        return pet;
    }
}
