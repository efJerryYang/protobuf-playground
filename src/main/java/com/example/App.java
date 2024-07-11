package com.example;

import com.example.tutorial.protos.*;

public class App 
{
    public static void main( String[] args )
    {
        Person john = Person.newBuilder()
            .setId(1234)
            .setName("John Doe")
            .setEmail("jdoe@example.com")
            .addPhones(
                Person.PhoneNumber.newBuilder()
                .setNumber("555-4321")
                .setType(Person.PhoneType.PHONE_TYPE_HOME)
                .build())
              .build();
        System.out.println( john );
        System.out.println(john.isInitialized());
        byte[] btarr = john.toByteArray();
        System.out.println(btarr);
        try {
            Person john2 = Person.parseFrom(btarr);
            System.out.println(john2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
