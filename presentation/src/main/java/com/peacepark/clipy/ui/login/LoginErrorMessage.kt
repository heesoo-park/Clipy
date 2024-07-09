package com.peacepark.clipy.ui.login

import androidx.annotation.StringRes
import com.peacepark.clipy.R

enum class LoginErrorMessage(
    @StringRes val message: Int
) {
    EMAIL_BLANK(R.string.login_email_blank_error),
    EMAIL_INVALID(R.string.login_email_invalid_error),

    PASSWORD_BLACK(R.string.login_password_blank_error),
    PASSWORD_NOT_CONTAIN_UPPERCASE(R.string.login_password_not_contain_uppercase_error),
    PASSWORD_NOT_CONTAIN_SPECIAL_CHAR(R.string.login_password_not_contain_special_char_error),
    PASSWORD_LESS_LENGTH(R.string.login_password_less_length_error),
    PASSWORD_MORE_LENGTH(R.string.login_password_more_length_error),
}