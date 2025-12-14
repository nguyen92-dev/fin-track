package top.nguyennd.expense.users;

import top.nguyennd.expense.users.dto.UserReqDto;
import top.nguyennd.expense.users.dto.UserResDto;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.IPagedListService;

public interface IUserService extends IPagedListService<UserResDto> {

    UserResDto createUser(UserReqDto reqDto);
}
