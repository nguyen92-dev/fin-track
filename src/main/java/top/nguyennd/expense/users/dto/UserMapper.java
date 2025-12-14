package top.nguyennd.expense.users.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import top.nguyennd.expense.users.entity.AppUser;

@Mapper(componentModel = "spring")
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserResDto toDto(AppUser user);

    AppUser toEntity(UserReqDto reqDto);
}
