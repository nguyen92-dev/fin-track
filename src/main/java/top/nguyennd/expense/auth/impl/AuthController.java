package top.nguyennd.expense.auth.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import top.nguyennd.expense.auth.IAuthContract;
import top.nguyennd.expense.auth.IAuthService;
import top.nguyennd.expense.auth.dto.LogInReqDto;
import top.nguyennd.expense.auth.dto.LogInResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController implements IAuthContract {

    IAuthService authService;

    @Override
    public ResponseEntity<BaseResponse<LogInResDto>> login(LogInReqDto reqDto) {
        LogInResDto loginRes = authService.login(reqDto);
        return ResponseEntity.ok(BaseResponse.buildSuccess(loginRes));
    }
}
