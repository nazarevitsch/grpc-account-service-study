package com.study.grpc.account.service.provider;

import com.study.grpc.account.client.*;
import com.study.grpc.account.service.service.PersonService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.LinkedList;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PersonProvider extends PersonServiceGrpc.PersonServiceImplBase {

    private final static int MILLISECONDS_TO_WAIT = 12345;
    private final PersonService personService;

    @Override
    public void create(Person request, StreamObserver<Person> responseObserver) {
        responseObserver.onNext(this.personService.create(request));
        responseObserver.onCompleted();
    }

    @Override
    public void get(Id request, StreamObserver<Person> responseObserver) {
        responseObserver.onNext(this.personService.get(request.getId()));
        responseObserver.onCompleted();
    }

    @Override
    public void getByIds(Ids request, StreamObserver<Persons> responseObserver) {
        responseObserver.onNext(this.personService.getByIds(request.getIdsList()));
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Id request, StreamObserver<Empty> responseObserver) {
        this.personService.delete(request.getId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getPersonsStream(Empty request, StreamObserver<Person> responseObserver) {
        Persons persons = this.personService.getAll();
        List<Long> ids = new LinkedList<>(persons.getPersonsList().stream().map(Person::getId).toList());

        persons.getPersonsList().forEach(responseObserver::onNext);

        int counter = 0;
        while (true) {
            List<Person> newPersons = this.personService.getIdsIsNotInScope(ids).getPersonsList();
            if (newPersons.isEmpty()) {
                if (counter == 2) {
                    break;
                } else {
                    counter++;
                    sleep();
                    continue;
                }
            }
            newPersons.forEach(responseObserver::onNext);
            ids.addAll(newPersons.stream().map(Person::getId).toList());
        }

        responseObserver.onCompleted();
    }

    private static void sleep() {
        try {
            Thread.sleep(MILLISECONDS_TO_WAIT);
        } catch (Exception e) {}
    }
}