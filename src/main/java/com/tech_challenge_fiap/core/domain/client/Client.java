package com.tech_challenge_fiap.core.domain.client;


import com.tech_challenge_fiap.util.exception.CpfWrongFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import com.tech_challenge_fiap.util.exception.NameCannotBeNullOrEmptyException;
import com.tech_challenge_fiap.util.exception.EmailCannotBeNullOrEmptyException;

@Getter
@Builder
public class Client {
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String cpf;

    @NonNull
    private String email;

    public static ClientBuilder builder() {
        return new CustomClientBuilder();
    }

    private static class CustomClientBuilder extends ClientBuilder {
        @Override
        public Client build() {
            validateName();
            validateEmail();
            validateCpf();
            return super.build();
        }

        private void validateName() {
            if (super.name == null || super.name.trim().isEmpty()) {
                throw new NameCannotBeNullOrEmptyException();
            }
        }

        private void validateEmail() {
            if (super.email == null || super.email.trim().isEmpty()) {
                throw new EmailCannotBeNullOrEmptyException();
            }
        }

        private void validateCpf() {
            var cpfFormated = super.cpf.replaceAll("[^0-9]", "");

            if (cpfFormated.length() != 11) {
                throw new CpfWrongFormat();
            }

            super.cpf = cpfFormated;
        }
    }
}
