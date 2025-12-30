package top.nguyennd.expense.expense.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import top.nguyennd.expense.expense.ExpenseCategoryRepository;
import top.nguyennd.expense.expense.IExpenseCategoryService;
import top.nguyennd.expense.expense.IExpenseService;
import top.nguyennd.expense.expense.dto.ExpenseCategoryMapper;
import top.nguyennd.expense.expense.dto.ExpenseCategoryReqDto;
import top.nguyennd.expense.expense.dto.ExpenseCategoryResDto;
import top.nguyennd.expense.expense.entities.ExpenseCategory;
import top.nguyennd.restsqlbackend.abstraction.service.AbstractCommonService;

import java.util.function.Function;

import static top.nguyennd.restsqlbackend.abstraction.exception.BusinessException.notFound;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseCategoryService extends AbstractCommonService<ExpenseCategory, ExpenseCategoryResDto> implements IExpenseCategoryService {

  ExpenseCategoryRepository repository;
  ExpenseCategoryMapper mapper;

  public ExpenseCategoryService(ExpenseCategoryRepository repository,
                                ExpenseCategoryMapper mapper) {
    super(repository);
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  protected Class<ExpenseCategory> getEntityClass() {
    return ExpenseCategory.class;
  }

  @Override
  protected Function<ExpenseCategory, ExpenseCategoryResDto> getMapper() {
    return mapper::toDto;
  }

  @Override
  public ExpenseCategoryResDto createExpenseCategory(ExpenseCategoryReqDto reqDto) {
    validateUniqueFields(reqDto, null);
    var entity = mapper.toEntity(reqDto);
    return mapper.toDto(repository.save(entity));
  }

  @Override
  public ExpenseCategoryResDto updateExpenseCategory(Long id, ExpenseCategoryReqDto reqDto) {
    ExpenseCategory savedEntity = repository.findById(id).orElseThrow(
        () -> notFound("Khong tim thay loai chi phi %s".formatted(id)));
    validateUniqueFields(reqDto, id);
    mapper.updateEntity(reqDto, savedEntity);
    return mapper.toDto(repository.save(savedEntity));
  }

}
