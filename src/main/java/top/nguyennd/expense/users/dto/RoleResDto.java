package top.nguyennd.expense.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RoleResDto {
    Long id;

    String name;

    String description;
}
