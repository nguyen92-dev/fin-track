package top.nguyennd.expense.users;

import jakarta.validation.constraints.NotNull;
import top.nguyennd.expense.users.entity.AppUser;
import top.nguyennd.restsqlbackend.abstraction.repository.IBaseRepository;

import java.util.Optional;

public interface AppUserRepository extends IBaseRepository<AppUser> {
    Optional<AppUser> findByUsernameIgnoreCase(@NotNull(message = "username không được để trống") String username);
}
