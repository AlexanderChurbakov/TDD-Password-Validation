package TDDVideoStazher

interface Validation {

    fun isValid(text: String): Result

    sealed class Result {
        object Valid : Result()

        data class MinLengthError(private val minLength: Int) : Result()
        data class UpperCaseLettersCountMinLengthError(private val upperCaseLettersCount: Int) : Result()
        data class LowerCaseLettersCountMinLengthError(private val lowerCaseLettersCount: Int) : Result()
        data class NumbersCountMinLengthError(private val lowerCaseLettersCount: Int) : Result()
        data class SpecialSignsCountMinLenghtError(private val lowerCaseLettersCount: Int) : Result()
    }

    class Password(
        private val minLength: Int = 1,
        private val upperCaseLettersCount: Int = 0,
        private val lowerCaseLettersCount: Int = 0,
        private val numbersCount: Int = 0,
        private val specialSignsCount: Int = 0,
    ) : Validation {

        init {
            if (minLength < 1) throw IllegalStateException(
                "minLength should be positive!"
            )
            if (upperCaseLettersCount < 0) throw IllegalStateException(
                "upperCaseLettersCount should be non negative!"
            )
            if (lowerCaseLettersCount < 0) throw IllegalStateException(
                "lowerCaseLettersCount should be non negative!"
            )
            if (numbersCount < 0) throw IllegalStateException(
                "numbersCount should be non negative!"
            )
            if (specialSignsCount < 0) throw IllegalStateException(
                "specialSignsCount should be non negative!"
            )

        }

        override fun isValid(text: String): Result {

            if (text.length < minLength) {
                return Result.MinLengthError(minLength)
            }

            if (upperCaseLettersCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isUpperCase()) count++
                }
                if (count < upperCaseLettersCount) {
                    return Result.UpperCaseLettersCountMinLengthError(upperCaseLettersCount)
                }
            }

            if (lowerCaseLettersCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isLowerCase()) count++
                }
                if (count < lowerCaseLettersCount) {
                    return Result.LowerCaseLettersCountMinLengthError(lowerCaseLettersCount)
                }
            }

            if (numbersCount > 0) {
                var count = 0
                for (ch in text) {
                    if (ch.isDigit()) count++
                }
                if (count < numbersCount) {
                    return Result.NumbersCountMinLengthError(numbersCount)
                }
            }

            if (specialSignsCount > 0) {
                var count = 0
                for (ch in text) {
                    if (!ch.isLetterOrDigit()) count++
                }
                if (count < specialSignsCount) {
                    return Result.SpecialSignsCountMinLenghtError(specialSignsCount)
                }
            }
            return Result.Valid
        }
    }
}