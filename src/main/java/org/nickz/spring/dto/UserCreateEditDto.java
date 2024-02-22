package org.nickz.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.nickz.spring.database.entity.Role;
import org.nickz.spring.validation.UserInfo;
import org.nickz.spring.validation.group.CreateAction;
import org.nickz.spring.validation.group.UpdateAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = UpdateAction.class)
public class UserCreateEditDto {

    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    Role role;

    Integer companyId;

    MultipartFile image;

}
