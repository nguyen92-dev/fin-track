package top.nguyennd.expense.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.restsqlbackend.abstraction.dto.AbstractDto;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResDto extends AbstractDto {
    Long id;

    String username;

    String email;

    String fullName;

    RoleResDto role;

    String createdBy;
    LocalDateTime createdAt;
    String updatedBy;
    LocalDateTime updatedAt;
}
