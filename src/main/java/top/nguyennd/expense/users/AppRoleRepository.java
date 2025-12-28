package top.nguyennd.expense.users;

import top.nguyennd.expense.users.entities.AppRole;
import top.nguyennd.restsqlbackend.abstraction.repository.IBaseRepository;

import java.util.Optional;

public interface AppRoleRepository extends IBaseRepository<AppRole> {
  Optional<AppRole> findByName(String user);
}
