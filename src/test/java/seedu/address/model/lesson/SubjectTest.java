package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Subject(""));
    }

    @Test
    void isValidSubject_invalidSubject_returnsFalse() {
        assertFalse(Subject.isValidSubject(""));
        assertFalse(Subject.isValidSubject("-"));
    }

    @Test
    void isValidSubject_validSubject_returnsTrue() {
        assertTrue(Subject.isValidSubject("English"));
    }
}