package com.github.vhromada.bookcase.web.fo

import com.github.vhromada.bookcase.web.validator.constraint.Password
import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * A class represents FO for account.
 *
 * @author Vladimir Hromada
 */
@Password
data class AccountFO(

        /**
         * Username
         */
        @field:NotBlank
        val username: String?,

        /**
         * Password
         */
        @field:NotBlank
        val password: String?,

        /**
         * Copy of password
         */
        @field:NotBlank
        val copyPassword: String?) : Serializable
