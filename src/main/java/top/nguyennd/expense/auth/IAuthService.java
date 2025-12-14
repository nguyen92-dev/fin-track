package top.nguyennd.expense.auth;

import top.nguyennd.expense.auth.dto.LogInReqDto;
import top.nguyennd.expense.auth.dto.LogInResDto;

public interface IAuthService {
    LogInResDto login(LogInReqDto reqDto);
}
