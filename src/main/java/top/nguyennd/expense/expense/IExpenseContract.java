package top.nguyennd.expense.expense;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.nguyennd.expense.expense.dto.ExpenseReqDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;


@RequestMapping("/api/expenses")
@Validated
public interface IExpenseContract {

  @PostMapping()
  ResponseEntity<BaseResponse<ExpenseResDto>> addExpense(@RequestBody @Valid ExpenseReqDto reqDto);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<ExpenseResDto>> updateExpense(@PathVariable Long id,
                                                            @RequestBody @Valid ExpenseReqDto reqDto);

  @PostMapping("/paged-list")
  ResponseEntity<BaseResponse<PagedModel<ExpenseResDto>>> pagedListExpense(Pageable pageable,
                                                                           @RequestParam(defaultValue = "true") Boolean isPaged,
                                                                           @RequestBody FilterReqDto filter);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<ExpenseResDto>> getExpenseById(@PathVariable Long id);

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> deleteExpenseById(@PathVariable Long id);
}
