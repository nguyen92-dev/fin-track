package top.nguyennd.expense.expense;

import top.nguyennd.expense.expense.dto.ExpenseCategoryReqDto;
import top.nguyennd.expense.expense.dto.ExpenseCategoryResDto;
import top.nguyennd.restsqlbackend.abstraction.service.ICommonService;


public interface IExpenseCategoryService extends ICommonService<ExpenseCategoryResDto> {
  ExpenseCategoryResDto createExpenseCategory(ExpenseCategoryReqDto reqDto);

  ExpenseCategoryResDto updateExpenseCategory(Long id, ExpenseCategoryReqDto reqDto);

}
