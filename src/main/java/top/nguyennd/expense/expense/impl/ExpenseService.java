package top.nguyennd.expense.expense.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import top.nguyennd.expense.expense.ExpenseCategoryRepository;
import top.nguyennd.expense.expense.ExpenseRepository;
import top.nguyennd.expense.expense.IExpenseService;
import top.nguyennd.expense.expense.dto.ExpenseMapper;
import top.nguyennd.expense.expense.dto.ExpenseReqDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.expense.expense.entities.Expense;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.AbstractPagedListService;

import java.time.LocalDate;
import java.util.function.Function;

import static top.nguyennd.restsqlbackend.abstraction.exception.BusinessException.badRequest;
import static top.nguyennd.restsqlbackend.abstraction.exception.BusinessException.notFound;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ExpenseService extends AbstractPagedListService<Expense, ExpenseResDto> implements IExpenseService {

  ExpenseMapper mapper;
  ExpenseRepository repository;
  ExpenseCategoryRepository categoryRepository;

  public ExpenseService(ExpenseMapper mapper,
                        ExpenseRepository repository,
                        ExpenseCategoryRepository categoryRepository) {
    super(repository);
    this.mapper = mapper;
    this.repository = repository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public ExpenseResDto addExpense(ExpenseReqDto reqDto) {
    validateRequestDto(reqDto);
    Expense entity = mapper.toEntity(reqDto);
    return mapper.toDto(repository.save(entity));
  }

  private void validateRequestDto(ExpenseReqDto reqDto) {
    if (reqDto.getExpenseDate().getYear() < LocalDate.now().getYear()) {
      throw badRequest("Ngày chi không được thuộc về các năm trước");
    }
    validateCategory(reqDto.getCategoryId());
  }

  private void validateCategory(@NotNull(message = "Category ID must not be null") Long categoryId) {
    categoryRepository.findById(categoryId).orElseThrow(
        () -> notFound("Loai chi chua duoc luu, vui long kiem tra lai")
    );
  }

  @Override
  public ExpenseResDto updateExpense(Long id, ExpenseReqDto reqDto) {
    validateRequestDto(reqDto);
    Expense entity = repository.findById(id).orElseThrow(
        () -> notFound("Không tìm thấy khoản chi với id: " + id)
    );
    mapper.updateEntity(reqDto, entity);
    return mapper.toDto(repository.save(entity));
  }

  @Override
  protected Class<Expense> getEntityClass() {
    return Expense.class;
  }

  @Override
  protected Function<Expense, ExpenseResDto> getMapper() {
    return mapper::toDto;
  }
}
