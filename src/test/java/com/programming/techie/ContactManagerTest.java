package com.programming.techie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {
    @Test
    void shouldCreateContact() {
        ContactManager contactManager = new ContactManager();
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
            .filter(contact -> contact.getFirstName().equals("John") &&
                    contact.getLastName().equals("Doe") &&
                    contact.getPhoneNumber().equals("0123456789"))
            .findAny()
            .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        ContactManager contactManager = new ContactManager();
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        ContactManager contactManager = new ContactManager();
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        ContactManager contactManager = new ContactManager();
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }
}