package top.nguyennd.expense.users.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import top.nguyennd.expense.users.IUserService;
import top.nguyennd.expense.users.UsersContract;
import top.nguyennd.expense.users.dto.UserReqDto;
import top.nguyennd.expense.users.dto.UserResDto;
import top.nguyennd.restsqlbackend.abstraction.dto.BaseResponse;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.FilterReqDto;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController implements UsersContract {
  IUserService userService;

  @Override
  public ResponseEntity<BaseResponse<PagedModel<UserResDto>>> getPageList(FilterReqDto filter, Pageable pageable, Boolean isPaged) {
    PagedModel<UserResDto> result = new PagedModel<>(userService.getPagedList(filter, pageable, isPaged));
    var response = BaseResponse.buildSuccess(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BaseResponse<UserResDto>> createUser(UserReqDto reqDto) {
    UserResDto result = userService.createUser(reqDto);
    var response = BaseResponse.buildSuccess(result);
    return ResponseEntity.ok(response);
  }
}
