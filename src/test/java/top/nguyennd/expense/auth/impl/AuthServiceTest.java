package top.nguyennd.expense.auth.impl;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import top.nguyennd.expense.auth.dto.LogInReqDto;
import top.nguyennd.expense.auth.dto.LogInResDto;
import top.nguyennd.expense.users.AppUserRepository;
import top.nguyennd.expense.users.entities.AppRole;
import top.nguyennd.expense.users.entities.AppUser;
import top.nguyennd.restsqlbackend.abstraction.exception.BusinessException;

import java.text.ParseException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static top.nguyennd.expense.common.AppConstant.LOGIN_FAIL_MSG;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private static final String TEST_SIGNER_KEY = "testSignerKeyThatIsAtLeast64BytesLongForHS512AlgorithmRequirement1234567890";
    private static final long TEST_VALID_DURATION = 3600L; // 1 hour in seconds
    private static final long TEST_REFRESHABLE_DURATION = 86400L; // 24 hours in seconds

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "signerKey", TEST_SIGNER_KEY);
        ReflectionTestUtils.setField(authService, "validDuration", TEST_VALID_DURATION);
        ReflectionTestUtils.setField(authService, "refreshableDuration", TEST_REFRESHABLE_DURATION);
    }

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {

        @Test
        @DisplayName("Should successfully login with valid credentials")
        void shouldLoginSuccessfullyWithValidCredentials() {
            // Arrange
            String username = "testuser";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";
            String fullName = "Test User";
            String roleName = "ADMIN";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name(roleName)
                    .description("Administrator")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName(fullName)
                    .email("test@example.com")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            assertNotNull(result);
            assertEquals(username, result.username());
            assertEquals(fullName, result.fullName());
            assertEquals(roleName, result.role());
            assertNotNull(result.accessToken());
            assertFalse(result.accessToken().isEmpty());

            verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
            verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        }

        @Test
        @DisplayName("Should throw exception when user is not found")
        void shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            String username = "nonexistent";
            String password = "password123";
            LogInReqDto loginReq = new LogInReqDto(username, password);

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.empty());

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                authService.login(loginReq);
            });

            assertTrue(exception.getMessage().contains(LOGIN_FAIL_MSG));
            verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
            verify(passwordEncoder, never()).matches(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when password is incorrect")
        void shouldThrowExceptionWhenPasswordIncorrect() {
            // Arrange
            String username = "testuser";
            String password = "wrongpassword";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                authService.login(loginReq);
            });

            assertTrue(exception.getMessage().contains(LOGIN_FAIL_MSG));
            verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
            verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        }

        @Test
        @DisplayName("Should handle username case insensitively")
        void shouldHandleUsernameCaseInsensitively() {
            // Arrange
            String username = "TestUser";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username.toLowerCase())
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            assertNotNull(result);
            verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
        }
    }

    @Nested
    @DisplayName("JWT Token Generation Tests")
    class TokenGenerationTests {

        @Test
        @DisplayName("Should generate valid JWT token with correct claims")
        void shouldGenerateValidJWTTokenWithCorrectClaims() throws ParseException {
            // Arrange
            String username = "testuser";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";
            String roleName = "ADMIN";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name(roleName)
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            assertNotNull(result.accessToken());

            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(result.accessToken());
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Verify claims
            assertEquals(username, claims.getSubject());
            assertEquals("nguyennd.top", claims.getIssuer());
            assertNotNull(claims.getJWTID());
            assertNotNull(claims.getIssueTime());
            assertNotNull(claims.getExpirationTime());

            // Verify expiration time is approximately correct (within 5 seconds tolerance)
            long expectedExpiration = Instant.now().plusSeconds(TEST_VALID_DURATION).toEpochMilli();
            long actualExpiration = claims.getExpirationTime().getTime();
            assertTrue(Math.abs(expectedExpiration - actualExpiration) < 5000,
                    "Expiration time should be within 5 seconds of expected");

            // Verify scope claim
            String scope = claims.getStringClaim("scope");
            assertEquals("ROLE_ADMIN", scope);

            // Verify roles claim
            assertNotNull(claims.getClaim("roles"));
        }

        @Test
        @DisplayName("Should generate different tokens for different login attempts")
        void shouldGenerateDifferentTokensForDifferentLoginAttempts() {
            // Arrange
            String username = "testuser";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result1 = authService.login(loginReq);
            LogInResDto result2 = authService.login(loginReq);

            // Assert
            assertNotEquals(result1.accessToken(), result2.accessToken(),
                    "Each login should generate a unique token");
        }

        @Test
        @DisplayName("Should include correct role in token for different user roles")
        void shouldIncludeCorrectRoleInToken() throws ParseException {
            // Arrange
            String username = "manager";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";
            String roleName = "MANAGER";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name(roleName)
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Manager User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            SignedJWT signedJWT = SignedJWT.parse(result.accessToken());
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            String scope = claims.getStringClaim("scope");
            assertEquals("ROLE_MANAGER", scope);
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle user with null full name")
        void shouldHandleUserWithNullFullName() {
            // Arrange
            String username = "testuser";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName(null)
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            assertNotNull(result);
            assertNull(result.fullName());
            assertNotNull(result.accessToken());
        }

        @Test
        @DisplayName("Should handle empty password")
        void shouldHandleEmptyPassword() {
            // Arrange
            String username = "testuser";
            String password = "";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> {
                authService.login(loginReq);
            });

            assertTrue(exception.getMessage().contains(LOGIN_FAIL_MSG));
        }

        @Test
        @DisplayName("Should handle special characters in username")
        void shouldHandleSpecialCharactersInUsername() {
            // Arrange
            String username = "test.user@domain";
            String password = "password123";
            String encodedPassword = "$2a$10$encodedPassword";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name("USER")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName("Test User")
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert
            assertNotNull(result);
            assertEquals(username, result.username());
            assertNotNull(result.accessToken());
        }
    }

    @Nested
    @DisplayName("Integration-like Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should complete full authentication flow")
        void shouldCompleteFullAuthenticationFlow() throws ParseException {
            // Arrange
            String username = "admin";
            String password = "admin123";
            String encodedPassword = "$2a$10$encodedPassword";
            String fullName = "Administrator";
            String roleName = "ADMIN";
            String email = "admin@example.com";

            LogInReqDto loginReq = new LogInReqDto(username, password);

            AppRole role = AppRole.builder()
                    .name(roleName)
                    .description("System Administrator")
                    .build();

            AppUser user = AppUser.builder()
                    .username(username)
                    .password(encodedPassword)
                    .fullName(fullName)
                    .email(email)
                    .role(role)
                    .build();

            when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

            // Act
            LogInResDto result = authService.login(loginReq);

            // Assert - Response fields
            assertNotNull(result);
            assertEquals(username, result.username());
            assertEquals(fullName, result.fullName());
            assertEquals(roleName, result.role());
            assertNotNull(result.accessToken());

            // Assert - Token content
            SignedJWT signedJWT = SignedJWT.parse(result.accessToken());
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            assertEquals(username, claims.getSubject());
            assertEquals("nguyennd.top", claims.getIssuer());
            assertEquals("ROLE_ADMIN", claims.getStringClaim("scope"));

            // Verify all interactions
            verify(userRepository, times(1)).findByUsernameIgnoreCase(username);
            verify(passwordEncoder, times(1)).matches(password, encodedPassword);
            verifyNoMoreInteractions(userRepository, passwordEncoder);
        }
    }
}

