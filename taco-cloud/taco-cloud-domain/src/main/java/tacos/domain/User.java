package tacos.domain;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity  {

    private String userName;

    private String password;

    private String phoneNumber;
}
