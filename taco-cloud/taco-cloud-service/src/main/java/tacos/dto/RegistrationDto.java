package tacos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegistrationDto {

    @ApiModelProperty(value = "用户名", required = true, dataType = "String")
    private String userName;

    @ApiModelProperty(value = "密码", required = true, dataType = "String")
    private String password;

    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String phoneNumber;
}