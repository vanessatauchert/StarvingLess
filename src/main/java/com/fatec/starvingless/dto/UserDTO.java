package com.fatec.starvingless.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.services.exceptions.InvalidCpfException;
import com.fatec.starvingless.services.exceptions.InvalidDateException;
import com.fatec.starvingless.services.exceptions.InvalidPhoneException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "Mandatory field")
    private String name;
    @CPF
    @NotNull(message = "Mandatory field")
    private String cpf;
    @NotNull(message = "Mandatory field")
    private String address;
    @JsonIgnore
    private String password;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Please verify your email")
    private String email;
    @NotNull(message = "Mandatory field")
    private String phone;
    @NotBlank(message = "The date cannot be future ")
    private String signUpDate;


    public UserDTO (User user){
        id = user.getId();
        name = user.getName();
        cpf = user.getCpf();
        address = user.getAddress();
        password = user.getPassword();
        email = user.getEmail();
        phone = user.getPhone();
        signUpDate = user.getSignUpDate();
    }

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public void setCpf(String cpf) {
        if (cpfIsValid(cpf)) {
            this.cpf = cpf;
        } else {
            throw new InvalidCpfException("Ex: xxx.xxx.xxx-xx");
        }
    }

    public void setPhone(String phone) {
        if (phoneIsValid(phone)) {
            this.phone = phone;
        } else {
            throw new InvalidPhoneException("Ex: (xx)xxxxx-xxxx");
        }
    }

    public void setSignUpDate(String signUpDate) {
        if (signUpDateIsValid(signUpDate)) {
            this.signUpDate = signUpDate;
        } else {
            throw new InvalidDateException("Ex: dd/MM/yyyy");
        }
    }

    private boolean cpfIsValid(String cpf) {
        return CPF_PATTERN.matcher(cpf).matches();
    }

    private boolean phoneIsValid(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private boolean signUpDateIsValid(String signUpDate) {
        try {
            Date date = DATE_FORMAT.parse(signUpDate);
            return signUpDate.equals(DATE_FORMAT.format(date));
        } catch (ParseException e) {
            return false;
        }
    }
}
