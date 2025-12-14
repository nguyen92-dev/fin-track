package top.nguyennd.expense.users;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.nguyennd.expense.users.dto.UserReqDto;
import top.nguyennd.expense.users.dto.UserResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;

@RequestMapping("api/users")
@Validated
public interface UsersContract {
    @PostMapping("/page-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<BaseResponse<PagedModel<UserResDto>>> getPageList(@RequestBody FilterReqDto filter, Pageable pageable);

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<BaseResponse<UserResDto>> createUser(@Valid @RequestBody UserReqDto reqDto);
}
