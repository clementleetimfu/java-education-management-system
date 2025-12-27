package io.clementleetimfu.educationmanagementsystem.utils.bcrypt;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BcryptUtilTest {

    private BcryptUtil bcryptUtil;

    @BeforeEach
    void setUp() {
        bcryptUtil = new BcryptUtil("test-pepper");
    }

    @Test
    @DisplayName("Test hash generates different values for same password")
    void testHashGeneratesDifferentValues() {
        String password = "password123";
        String hash1 = bcryptUtil.hash(password);
        String hash2 = bcryptUtil.hash(password);

        assertNotEquals(hash1, hash2, "BCrypt should generate different hashes for the same password");
        assertTrue(hash1.startsWith("$2a$10$"), "Hash should start with BCrypt identifier");
        assertTrue(hash2.startsWith("$2a$10$"), "Hash should start with BCrypt identifier");
    }

    @Test
    @DisplayName("Test hash generates consistent length")
    void testHashConsistentLength() {
        String password = "password123";
        String hash1 = bcryptUtil.hash(password);
        String hash2 = bcryptUtil.hash("differentpassword");

        assertEquals(60, hash1.length(), "BCrypt hash should be 60 characters");
        assertEquals(60, hash2.length(), "BCrypt hash should be 60 characters");
    }

    @Test
    @DisplayName("Test verify returns true for correct password")
    void testVerifyCorrectPassword() {
        String password = "correctPassword";
        String hashedPassword = bcryptUtil.hash(password);

        boolean result = bcryptUtil.verify(password, hashedPassword);

        assertTrue(result, "Verification should succeed for correct password");
    }

    @Test
    @DisplayName("Test verify returns false for incorrect password")
    void testVerifyIncorrectPassword() {
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        String hashedPassword = bcryptUtil.hash(correctPassword);

        boolean result = bcryptUtil.verify(wrongPassword, hashedPassword);

        assertFalse(result, "Verification should fail for incorrect password");
    }

    @Test
    @DisplayName("Test verify returns false for different pepper")
    void testVerifyDifferentPepper() {
        String password = "password123";
        BcryptUtil bcryptUtilDifferentPepper = new BcryptUtil("different-pepper");
        String hashedPassword = bcryptUtil.hash(password);

        boolean result = bcryptUtilDifferentPepper.verify(password, hashedPassword);

        assertFalse(result, "Verification should fail when pepper differs");
    }

    @Test
    @DisplayName("Test hash and verify with empty string")
    void testHashVerifyEmptyString() {
        String password = "";
        String hashedPassword = bcryptUtil.hash(password);

        boolean result = bcryptUtil.verify(password, hashedPassword);

        assertTrue(result, "Verification should succeed for empty string password");
    }

    @Test
    @DisplayName("Test hash and verify with special characters")
    void testHashVerifySpecialCharacters() {
        String password = "p@ssw0rd!#$%^&*()";
        String hashedPassword = bcryptUtil.hash(password);

        boolean result = bcryptUtil.verify(password, hashedPassword);

        assertTrue(result, "Verification should succeed for password with special characters");
    }

    @Test
    @DisplayName("Test verify returns false for null hash")
    void testVerifyNullHash() {
        boolean result = bcryptUtil.verify("password123", null);
        assertFalse(result, "Verification should return false for null hash");
    }

    @Test
    @DisplayName("Test null password handling")
    void testNullPasswordHandling() {
        assertThrows(BusinessException.class, () -> bcryptUtil.hash(null));
    }

    @Test
    @DisplayName("Test verify returns false for invalid hash format")
    void testVerifyInvalidHashFormat() {
        String password = "password123";
        String invalidHash = "invalid-hash-format";

        boolean result = bcryptUtil.verify(password, invalidHash);

        assertFalse(result, "Verification should fail for invalid hash format");
    }

    @Test
    @DisplayName("Test hash includes pepper in calculation")
    void testHashIncludesPepper() {
        String password = "password123";
        BcryptUtil bcryptUtilPepper1 = new BcryptUtil("pepper1");
        BcryptUtil bcryptUtilPepper2 = new BcryptUtil("pepper2");

        String hash1 = bcryptUtilPepper1.hash(password);
        String hash2 = bcryptUtilPepper2.hash(password);

        assertNotEquals(hash1, hash2, "Hashes should differ when pepper differs");
    }

}