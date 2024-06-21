package ar.org.schoolsync.dto.auth

data class AuthenticationRequest(val email: String?, val password: String?) {
    init {
        require(!email.isNullOrEmpty()) { "Email cannot be null or empty" }
        require(!password.isNullOrEmpty()) { "Password cannot be null or empty" }
    }
}
