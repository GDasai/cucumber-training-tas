package com.cucumber.definitions.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationObject {

    private String firstName;
    private String prefix;
    private String surname;
    private String dateOfBirth;
    private String profession;
    private String gender;
    private String remarks;
}
