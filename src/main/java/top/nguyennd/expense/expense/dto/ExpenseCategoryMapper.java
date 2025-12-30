package top.nguyennd.expense.expense.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import top.nguyennd.expense.expense.entities.ExpenseCategory;
import top.nguyennd.restsqlbackend.abstraction.annotation.IgnoreAuditFields;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExpenseCategoryMapper {

  @IgnoreAuditFields
  ExpenseCategory toEntity(ExpenseCategoryReqDto reqDto);

  ExpenseCategoryResDto toDto(ExpenseCategory entity);

  @IgnoreAuditFields
  void updateEntity(ExpenseCategoryReqDto reqDto, @MappingTarget ExpenseCategory entity);
}
