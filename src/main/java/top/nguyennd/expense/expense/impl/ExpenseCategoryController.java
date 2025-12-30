package top.nguyennd.expense.expense.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import top.nguyennd.expense.expense.ExpenseCategoryContract;
import top.nguyennd.expense.expense.IExpenseCategoryService;
import top.nguyennd.expense.expense.dto.ExpenseCategoryReqDto;
import top.nguyennd.expense.expense.dto.ExpenseCategoryResDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;

import java.net.URI;
import java.util.List;

import static top.nguyennd.restsqlbackend.abstraction.exception.BusinessException.notFound;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExpenseCategoryController implements ExpenseCategoryContract {

  IExpenseCategoryService service;

  @Override
  public ResponseEntity<BaseResponse<ExpenseCategoryResDto>> createExpenseCategory(ExpenseCategoryReqDto reqDto) {
    ExpenseCategoryResDto result = service.createExpenseCategory(reqDto);
    URI location = URI.create("/api/expense-categories/" + result.getId());
    var response = BaseResponse.<ExpenseCategoryResDto>buildCreatedSuccess(result);
    return ResponseEntity.created(location).body(response);
  }

  @Override
  public ResponseEntity<BaseResponse<ExpenseCategoryResDto>> updateExpenseCategory(Long id, ExpenseCategoryReqDto reqDto) {
    ExpenseCategoryResDto result = service.updateExpenseCategory(id, reqDto);
    var response = BaseResponse.<ExpenseCategoryResDto>buildSuccess(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BaseResponse<ExpenseCategoryResDto>> getExpenseCategoryById(Long id) {
    ExpenseCategoryResDto result = service.findById(id).orElseThrow(
        () -> notFound("Khong tim thay loai khoan chi %s".formatted(id)));
    return ResponseEntity.ok(BaseResponse.<ExpenseCategoryResDto>buildSuccess(result));
  }

  @Override
  public ResponseEntity<BaseResponse<List<ExpenseCategoryResDto>>> getAllExpenseCategories() {
    var result = service.findAll();
    return ResponseEntity.ok(BaseResponse.<List<ExpenseCategoryResDto>>buildSuccess(result));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> deleteExpenseCategoryById(Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
