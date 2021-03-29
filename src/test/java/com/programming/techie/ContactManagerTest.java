package com.programming.techie;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Should Create Contact")
    void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream().anyMatch(this::isJohnDoe));
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact(null, "Doe", "0123456789"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact("John", null, "0123456789"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact("John", "Doe", null));
    }

    private boolean isJohnDoe(Contact contact) {
        return contact.getFirstName().equals("John") &&
            contact.getLastName().equals("Doe") &&
            contact.getPhoneNumber().equals("0123456789");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should Execute After All Tests");
    }

    @Test
    @DisplayName("Should Create Contact only on Mac OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
    void shouldCreateContactOnlyOnMac() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should Not Create Contact only on Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled On Windows OS")
    void shouldCreateContactOnlyOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationDev() {
        assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Repeat Contact Creation Test 5 Times")
    @RepeatedTest(value = 5,
        name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
    public void shouldTestContactCreationRepeatedly() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Repeat Contact Creation Test 5 Times")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return List.of("0123456789", "0123456798", "0123456897");
    }

    @DisplayName("CSV Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvSource({"0123456789", "0123456798", "0123456897"})
    public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("CSV Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }
}