package top.nguyennd.expense.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.restsqlbackend.abstraction.dto.AbstractDto;

import java.time.LocalDateTime;

@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditedDto extends AbstractDto {
  String createdBy;
  LocalDateTime createdAt;
  String updatedBy;
  LocalDateTime updatedAt;
}
