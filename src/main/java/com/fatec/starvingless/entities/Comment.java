package com.fatec.starvingless.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fatec.starvingless.services.exceptions.InvalidDateException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @javax.validation.constraints.Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-!,]*$",
            message = "Description cannot contain special characters")
    @Size(max = 250, message = "Description must be between 10 and 250 chars")
    @NotBlank
    private String description;

    @NotBlank(message = "Required field")
    private String createDate;

    private String firstName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(User user){
        this.user = user;
        firstName = user.getFirstName();
    }
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public void setCreateDate(String createDate) {
        if (createDateIsValid(createDate)) {
            try {
                Date date = DATE_FORMAT.parse(createDate);
                Date currentDate = new Date();
                if (date.after(currentDate)) {
                    throw new InvalidDateException("SignUpDate cannot be greater than the current date");
                }
                this.createDate = createDate;
            } catch (ParseException e) {
                throw new InvalidDateException("Ex: dd/MM/yyyy");
            }
        } else {
            throw new InvalidDateException("Ex: dd/MM/yyyy");
        }
    }

    private boolean createDateIsValid(String createDate) {
        try {
            Date date = DATE_FORMAT.parse(createDate);
            return createDate.equals(DATE_FORMAT.format(date));
        } catch (ParseException e) {
            return false;
        }
    }
}
