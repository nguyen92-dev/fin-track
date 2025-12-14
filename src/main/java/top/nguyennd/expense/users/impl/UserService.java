package top.nguyennd.expense.users.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.nguyennd.expense.users.AppRoleRepository;
import top.nguyennd.expense.users.AppUserRepository;
import top.nguyennd.expense.users.IUserService;
import top.nguyennd.expense.users.dto.UserMapper;
import top.nguyennd.expense.users.dto.UserReqDto;
import top.nguyennd.expense.users.dto.UserResDto;
import top.nguyennd.expense.users.entity.AppUser;
import top.nguyennd.restsqlbackend.abstraction.pagedlist.AbstractPagedListService;

import java.util.function.Function;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService extends AbstractPagedListService<AppUser, UserResDto> implements IUserService {

    AppUserRepository repository;
    UserMapper mapper;
    PasswordEncoder passwordEncoder;
    AppRoleRepository roleRepository;

    public UserService(AppUserRepository repository,
                       UserMapper mapper,
                       PasswordEncoder passwordEncoder,
                       AppRoleRepository roleRepository) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    protected Class<AppUser> getEntityClass() {
        return AppUser.class;
    }

    @Override
    protected Function<AppUser, UserResDto> getMapper() {
        return mapper::toDto;
    }

    @Override
    @Transactional
    public UserResDto createUser(UserReqDto reqDto) {
        validateUniqueField("username", reqDto.username(), null);
        var user = mapper.toEntity(reqDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var role = roleRepository.findByName("USER").orElse(null);
        user.setRole(role);
        return mapper.toDto(repository.save(user));
    }
}
