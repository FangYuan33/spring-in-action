package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Order {

    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message="Street is required")
    private String street;

    private String city;

    private String state;

    private String zip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;
}
