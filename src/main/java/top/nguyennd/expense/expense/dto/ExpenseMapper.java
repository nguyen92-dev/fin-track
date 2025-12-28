package top.nguyennd.expense.expense.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import top.nguyennd.expense.expense.entities.Expense;
import top.nguyennd.restsqlbackend.abstraction.annotation.IgnoreAuditFields;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExpenseMapper {

  @IgnoreAuditFields
  Expense toEntity(ExpenseReqDto reqDto);

  ExpenseResDto toDto(Expense expense);

  @IgnoreAuditFields
  void updateEntity(ExpenseReqDto reqDto, @MappingTarget Expense entity);
}
