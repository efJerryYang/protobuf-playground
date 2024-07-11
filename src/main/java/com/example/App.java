package com.example;

import java.io.*;

import com.example.tutorial.AddPeople;
import com.example.tutorial.ListPeople;
import com.example.tutorial.protos.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("########### Build Person ###########");
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
        System.out.println(john);
        System.out.println(john.isInitialized());
        byte[] btarr = john.toByteArray();
        System.out.println(btarr);
        try {
            Person john2 = Person.parseFrom(btarr);
            System.out.println(john2);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("########### Build AddressBook ###########");
        AddressBook.Builder addressBook = AddressBook.newBuilder();
        // addressbook path
        String addressBookPath = "addressbook.data";
        try {
            addressBook.mergeFrom(new FileInputStream(addressBookPath));
        } catch (FileNotFoundException e) {
            System.out.println("addressbook.data not found, creating new one");
        }

        addressBook.addPeople(
                AddPeople.PromptForAddress(new BufferedReader(new InputStreamReader(System.in)),
                        System.out));
        
        addressBook.addPeople(john);
        FileOutputStream fos = new FileOutputStream(addressBookPath);
        addressBook.build().writeTo(fos);
        fos.close();

        System.out.println("########### Read AddressBook ###########");

        AddressBook addressBook2 = AddressBook.parseFrom(new FileInputStream(addressBookPath));
        ListPeople.Print(addressBook2);

    }
}
