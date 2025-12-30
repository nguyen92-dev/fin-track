package top.nguyennd.expense.expense.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import top.nguyennd.restsqlbackend.abstraction.dto.AbstractDto;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryReqDto extends AbstractDto {
  @NotBlank(message = "Name must not be blank")
  @NotNull(message = "Name is required")
  @Length(min = 3, max = 100)
  String name;
  String description;
}
