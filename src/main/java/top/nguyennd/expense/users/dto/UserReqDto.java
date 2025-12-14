package top.nguyennd.expense.users.dto;

import jakarta.validation.constraints.NotBlank;

public record UserReqDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        String email,
        String fullName
) {
}
