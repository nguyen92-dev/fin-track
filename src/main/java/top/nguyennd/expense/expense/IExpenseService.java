package top.nguyennd.expense.expense;

import top.nguyennd.expense.expense.dto.ExpenseReqDto;
import top.nguyennd.expense.expense.dto.ExpenseResDto;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.IPagedListService;

public interface IExpenseService extends IPagedListService<ExpenseResDto> {
  ExpenseResDto addExpense(ExpenseReqDto reqDto);

  ExpenseResDto updateExpense(Long id, ExpenseReqDto reqDto);

}
