package top.nguyennd.expense.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.expense.abstracts.AuditedDto;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResDto extends AuditedDto {
  Long id;

  String username;

  String email;

  String fullName;

  RoleResDto role;
}
