package top.nguyennd.expense.expense.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.expense.abstracts.AuditedDto;
import top.nguyennd.expense.common.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseResDto extends AuditedDto {
  Integer id;
  LocalDate expenseDate;
  Integer categoryId;
  BigDecimal amount;
  String details;
  String address;
  PaymentMethod paymentMethod;
  String paymentBy;
}
