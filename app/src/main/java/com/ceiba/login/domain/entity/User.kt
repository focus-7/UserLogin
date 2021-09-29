package com.ceiba.login.domain.entity

class User(builder: UserBuilder) {
    var name: String = ""
        private set
    var phone: String = ""
        private set
    var email: String = ""
        private set
    var password: String = ""
        private set
    var userCreationDate: String = ""
        private set

    init {
        name = builder.name
        email = builder.email
        password = builder.password
        phone = builder.phone
        userCreationDate = builder.userCreationDate
    }
}