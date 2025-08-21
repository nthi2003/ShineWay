package com.mycompany.myapp.domain;

import java.util.UUID;

public class ProfileTestSamples {

    public static Profile getProfileSample1() {
        return new Profile()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .firstName("firstName1")
            .lastName("lastName1")
            .phone("phone1")
            .email("email1")
            .address("address1")
            .gender("gender1");
    }

    public static Profile getProfileSample2() {
        return new Profile()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .firstName("firstName2")
            .lastName("lastName2")
            .phone("phone2")
            .email("email2")
            .address("address2")
            .gender("gender2");
    }

    public static Profile getProfileRandomSampleGenerator() {
        return new Profile()
            .id(UUID.randomUUID())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .gender(UUID.randomUUID().toString());
    }
}
