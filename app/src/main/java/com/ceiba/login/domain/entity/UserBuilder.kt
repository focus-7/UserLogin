package com.ceiba.login.domain.entity

import com.ceiba.login.domain.util.ConvertDate

class UserBuilder {
    var name: String = ""
        private set
    var email: String = ""
        private set
    var password: String = ""
        private set
    var phone: String = ""
        private set
    var userCreationDate: String = ConvertDate.getTimeFormat()
        private set

    fun name(value: String): UserBuilder {
        this.name = value
        return this
    }

    fun email(value: String): UserBuilder {
        this.email = value
        return this
    }

    fun password(value: String): UserBuilder {
        this.password = value
        return this
    }

    fun phone(value: String): UserBuilder {
        this.phone = value
        return this
    }

    fun userCreationDate(value: String): UserBuilder {
        this.userCreationDate = value
        return this
    }

    fun build() = User(this)
}