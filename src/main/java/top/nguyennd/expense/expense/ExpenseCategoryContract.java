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
import top.nguyennd.expense.expense.dto.ExpenseCategoryReqDto;
import top.nguyennd.expense.expense.dto.ExpenseCategoryResDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;

import java.util.List;

@RequestMapping("api/expense-categories")
@Validated
public interface ExpenseCategoryContract {

  @PostMapping()
  @Operation(summary = "Create Expense Category")
  ResponseEntity<BaseResponse<ExpenseCategoryResDto>> createExpenseCategory(@Valid @RequestBody ExpenseCategoryReqDto reqDto);

  @PutMapping("/{id}")
  @Operation(summary = "Update Expense Category")
  ResponseEntity<BaseResponse<ExpenseCategoryResDto>> updateExpenseCategory(@PathVariable Long id,
                                                                            @RequestBody @Valid ExpenseCategoryReqDto reqDto);

  @GetMapping("/{id}")
  @Operation(summary = "Get Expense Category by ID")
  ResponseEntity<BaseResponse<ExpenseCategoryResDto>> getExpenseCategoryById(@PathVariable Long id);

  @GetMapping("")
  @Operation(summary = "Get All of Expense Categories")
  ResponseEntity<BaseResponse<List<ExpenseCategoryResDto>>> getAllExpenseCategories();

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Expense Category by ID")
  ResponseEntity<BaseResponse<Void>> deleteExpenseCategoryById(@PathVariable Long id);
}
