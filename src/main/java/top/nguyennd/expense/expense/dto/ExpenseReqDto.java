package top.nguyennd.expense.expense.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.expense.common.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseReqDto {
  @NotNull(message = "Expense date must not be null")
  LocalDate expenseDate;
  @NotNull(message = "Category ID must not be null")
  Long categoryId;
  @NotNull(message = "Amount must not be null")
  @Min(value = 1000, message = "Amount must be at least 1000")
  BigDecimal amount;
  String details;
  String address;
  PaymentMethod paymentMethod = PaymentMethod.BANK_TRANSFER;
  String paymentBy;
}
