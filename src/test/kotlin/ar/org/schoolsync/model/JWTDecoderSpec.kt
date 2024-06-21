package ar.org.schoolsync.model


import ar.org.schoolsync.controller.adminToken
import ar.org.schoolsync.model.utils.JWTDecoder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class JWTDecoderSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests JWT Decoder") {
        it("Should full decode a token") {
            val tkn = JWTDecoder(adminToken)
                .build()

            tkn.size shouldBe 5
            tkn["sub"] shouldBe "adminuser@schoolsync.mail.com"
            tkn["userId"] shouldBe 1
            tkn["role"] shouldBe "ADMIN"
            tkn["exp"] shouldBe 1718983095
        }

        it("Should partially decode a token") {
            val tkn = JWTDecoder(adminToken)
                .setClaims(setOf("userId", "role"))
                .build()

            tkn.size shouldBe 2
            tkn["userId"] shouldBe 1
            tkn["role"] shouldBe "ADMIN"
            tkn["exp"] shouldBe null
        }

        it("Should throw a error if the token is empty") {
            val emptyToken = ""
            shouldThrow<IllegalArgumentException> {
                JWTDecoder(emptyToken)
                    .build()
            }
        }

        it("Should return a empty map if the token is malformed") {
            val emptyToken = "asdf"

            val tkn = JWTDecoder(emptyToken)
                .build()

            tkn.size shouldBe 0
        }
    }
})