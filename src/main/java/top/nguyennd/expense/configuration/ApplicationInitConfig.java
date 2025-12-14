package top.nguyennd.expense.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.nguyennd.expense.users.AppRoleRepository;
import top.nguyennd.expense.users.AppUserRepository;
import top.nguyennd.expense.users.entity.AppRole;
import top.nguyennd.expense.users.entity.AppUser;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${ADMIN_USERNAME}")
    String adminUserName;

    @NonFinal
    @Value("${ADMIN_PASSWORD}")
    String adminPassword;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(AppUserRepository userRepository, AppRoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            AppRole adminRole = new AppRole();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Quản trị hệ thống");
            AppRole admin = initRoleIfNotFound(adminRole, roleRepository);
            AppRole userRole = new AppRole();
            userRole.setName("USER");
            userRole.setDescription("Người dùng hệ thống");
            initRoleIfNotFound(userRole, roleRepository);
            if (userRepository.findByUsernameIgnoreCase(adminUserName).isEmpty()) {
                AppUser user = AppUser.builder()
                        .username(adminUserName)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(admin)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: {}, please change it", adminPassword);
            }
            log.info("Khoi tao ung dung thanh cong .....");
        };
    }

    private AppRole initRoleIfNotFound(AppRole role, AppRoleRepository roleRepository) {
        AppRole savedRole = roleRepository.findByName(role.getName()).orElse(null);
        if (savedRole == null) {
            savedRole = roleRepository.save(role);
            log.info("Khoi tao thanh cong role: {}", role.getName());
        }
        return savedRole;
    }
}
