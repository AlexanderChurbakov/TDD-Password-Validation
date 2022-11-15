package TDDVideoStazher

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class PasswordValidationTest {

    //region no arguments into constructor
    @Test
    fun test_default() {
        val validation: Validation = Validation.Password()
        val sourseList = listOf<String>(
            "12", "aa", "  ", " ",
            "!@@", "@21a", "aaaBB",
            "a@1VB2!aa", "sda!@sam M2KA!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_empty() {
        val validation: Validation = Validation.Password()
        val actual = validation.isValid("")
        assertEquals(Validation.Result.MinLengthError
            (minLength = 1), actual)
    }
    //endregion

    // region min length
    @Test
    fun test_min_length_valid() {
        val validation: Validation = Validation.Password(minLength = 2)
        val sourseList = listOf<String>(
            "12", "aa", "  m", "  ",
            "!@@", "@21a", "aaaBB",
            "a@1VB2!aa", "sda!@sam M2KA!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_min_length_invalid() {
        val validation: Validation = Validation.Password(minLength = 2)
        val sourseList = listOf<String>(
            "a", " ", "1", "@"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.MinLengthError(minLength = 2), it)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_min_length_zero() {
        Validation.Password(minLength = 0)
    }

    @Test(expected = IllegalStateException::class)
    fun test_min_length_negative() {
        Validation.Password(minLength = -1)
    }
    // endregion

    // region upper case letters
    @Test
    fun test_min_length_upperCaseLettersCount_valid() {
        val validation: Validation = Validation.Password(upperCaseLettersCount = 1)
        val sourseList = listOf<String>(
            "A", "A", "S", "M", "I",
            "1N2", "aNa", " N ", " O",
            "!@N@", "@2N1a", "aaaBB",
            "a@1VB2!aa", "sdaN!@sam M2KA!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_min_length_upperCaseLettersCount_invalid() {
        val validation: Validation = Validation.Password(upperCaseLettersCount = 1)
        val sourseList = listOf<String>(
            "m", "1", "@", " "
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.UpperCaseLettersCountMinLengthError(
                upperCaseLettersCount = 1), it)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_upperCaseLettersCount_negative() {
        Validation.Password(upperCaseLettersCount = -1)
    }

    // endregion

    // region lower case letters
    @Test
    fun test_min_length_lowerCaseLettersCount_valid() {
        val validation: Validation = Validation.Password(lowerCaseLettersCount = 1)
        val sourseList = listOf<String>(
            "a", "n", "s", "m", "i",
            "1Nn2", "aNa", " n ", " o",
            "!@v@", "@2N1a", "aaaBB",
            "a@1VB2!aa", "sdaN!@sam M2KAb!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_min_length_lowerCaseLettersCount_invalid() {
        val validation: Validation = Validation.Password(lowerCaseLettersCount = 1)
        val sourseList = listOf<String>(
            "M", "1", "@", " "
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.LowerCaseLettersCountMinLengthError(lowerCaseLettersCount = 1), it)
        }
    }

    // TODO: 15.11.2022
    @Test(expected = IllegalStateException::class)
    fun test_lowerCaseLettersCount_negative() {
        Validation.Password(lowerCaseLettersCount = -1)
    }
    //endregion

    // region numbers count
    @Test
    fun test_numbersCount_valid() {
        val validation: Validation = Validation.Password(numbersCount = 1)
        val sourseList = listOf<String>(
            "1", "3", "4", "9", "0",
            "1N2", "a2Na", " 2n ", "2 o",
            "!@2@", "@2N1a", "aa2aBB",
            "a@1VB2!aa", "sda7N!@sam M2KA!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_numbersCount_invalid() {
        val validation: Validation = Validation.Password(lowerCaseLettersCount = 1)
        val sourseList = listOf<String>(
            "M", "!", "@", " "
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.LowerCaseLettersCountMinLengthError(lowerCaseLettersCount = 1), it)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_numbersCount_negative() {
        Validation.Password(numbersCount = -1)
    }
    // endregion

    // region special signs
    @Test
    fun test_specialSignsCount_valid() {
        val validation: Validation = Validation.Password(specialSignsCount = 1)
        val sourseList = listOf<String>(
            "!", "@", "#", "$", "%",
            "1N%2", "a2N#a", " 2#n ", "2 *o",
            "!@2@", "@2N1a", "aa2!aBB",
            "a@1VB2!aa", "sda7N!@sam M2@KA!12"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.Valid, it)
        }
    }

    @Test
    fun test_specialSignsCount_invalid() {
        val validation: Validation = Validation.Password(specialSignsCount = 1)
        val sourseList = listOf<String>(
            "M", "1", "a"
        )
        val actualList: List<Validation.Result> = sourseList.map {
            validation.isValid(it)
        }
        actualList.forEach {
            assertEquals(Validation.Result.SpecialSignsCountMinLenghtError(lowerCaseLettersCount = 1), it)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun test_specialSigns_negative() {
        Validation.Password(numbersCount = -1)
    }

    //endregion
}