package top.nguyennd.expense.expense.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import top.nguyennd.expense.expense.ExpenseContract;
import top.nguyennd.expense.expense.IExpenseService;
import top.nguyennd.expense.expense.dto.ExpenseReqDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;

import java.net.URI;

import static top.nguyennd.restsqlbackend.abstraction.exception.BusinessException.notFound;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExpenseController implements ExpenseContract {

  IExpenseService expenseService;

  @Override
  public ResponseEntity<BaseResponse<ExpenseResDto>> addExpense(ExpenseReqDto reqDto) {
    ExpenseResDto result = expenseService.addExpense(reqDto);
    URI location = URI.create("/api/expenses/" + result.getId());
    var response = BaseResponse.<ExpenseResDto>builder()
        .status(201)
        .message("created successfully")
        .data(result)
        .build();
    return ResponseEntity.created(location).body(response);
  }

  @Override
  public ResponseEntity<BaseResponse<ExpenseResDto>> updateExpense(Long id, ExpenseReqDto reqDto) {
    ExpenseResDto result = expenseService.updateExpense(id, reqDto);
    var response = BaseResponse.buildSuccess(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BaseResponse<PagedModel<ExpenseResDto>>> pagedListExpense(Pageable pageable, Boolean isPaged, FilterReqDto filter) {
    PagedModel<ExpenseResDto> result = new PagedModel<>(expenseService.getPagedList(filter, pageable, isPaged));
    var response = BaseResponse.buildSuccess(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BaseResponse<ExpenseResDto>> getExpenseById(Long id) {
    ExpenseResDto result = expenseService.findById(id).orElseThrow(
        () -> notFound("Không tìm thấy khoản chi với id: " + id)
    );
    var response = BaseResponse.buildSuccess(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> deleteExpenseById(Long id) {
    expenseService.deleteById(id);
    var response = BaseResponse.buildSuccess();
    return ResponseEntity.ok(response);
  }
}
