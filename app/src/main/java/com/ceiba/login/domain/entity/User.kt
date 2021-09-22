package com.ceiba.login.domain.entity

class User private constructor(builder: UserBuilder) {
    var name: String = ""
        private set
    var phone: String = ""
        private set
    var email: String = ""
        private set
    var password: String = ""
        private set

    init {
        name = builder.name
        email = builder.email
        password = builder.password
        phone = builder.phone
    }

    class UserBuilder {
        var name: String = ""
            private set
        var email: String = ""
            private set
        var password: String = ""
            private set
        var phone: String = ""
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

        fun build() = User(this)
    }
}