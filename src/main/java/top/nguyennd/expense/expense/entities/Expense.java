package top.nguyennd.expense.expense.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import top.nguyennd.expense.common.enums.PaymentMethod;
import top.nguyennd.restsqlbackend.abstraction.entity.AbstractEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "expenses")
@Data
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Expense extends AbstractEntity {
  @Column(name = "expense_date")
  LocalDate expenseDate;

  @Column(name = "expense_category_id")
  Long categoryId;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "details")
  String details;

  @Column(name = "address")
  String address;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  PaymentMethod paymentMethod;

  @Column(name = "payment_by")
  String paymentBy;
}
