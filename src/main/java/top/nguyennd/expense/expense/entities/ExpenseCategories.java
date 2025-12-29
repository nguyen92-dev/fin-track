package top.nguyennd.expense.expense.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import top.nguyennd.restsqlbackend.abstraction.entity.AbstractEntity;

@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "expense_categories")
@Data
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseCategories extends AbstractEntity {
//  @Column(name = "name", unique = true, nullable = false)
//  String name;
//
//  @Column(name = "description")
//  String description;
}
