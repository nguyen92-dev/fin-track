package top.nguyennd.expense.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.nguyennd.expense.auth.dto.LogInReqDto;
import top.nguyennd.expense.auth.dto.LogInResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;

@RequestMapping("api/auth")
public interface IAuthContract {

    @PostMapping("/login")
    ResponseEntity<BaseResponse<LogInResDto>> login(@RequestBody @Valid LogInReqDto reqDto);
}
