package ar.org.schoolsync.dto.auth

data class AuthenticationRequest(val email: String?, val password: String?) {
    init {
        require(!email.isNullOrEmpty()) { "Email cannot be null or empty" }
        // You can add similar validation for password if needed
    }
}
