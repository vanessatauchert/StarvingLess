package com.fatec.starvingless.dto;

import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.services.exceptions.InvalidDateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    @Size(min = 10, max = 50, message = "Title must be between 10 and 50 chars")
    private String title;
    @Size(min = 10, max = 250, message = "Description must be between 10 and 250 chars")
    @NotBlank
    private String description;
    private URL image;
    private String datetime;
    private boolean threadOpen;
    private Integer numberOfComments;

    public PostDTO(Post entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        image = entity.getImage();
        datetime = entity.getDatetime();
        threadOpen = entity.isThreadOpen();
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public void setDateTime(String datetime) {
        if (dateTimeIsValid(datetime)) {
            try {
                Date date = DATE_FORMAT.parse(datetime);
                Date currentDate = new Date();
                if (date.after(currentDate)) {
                    throw new InvalidDateException("SignUpDate cannot be greater than the current date");
                }
                this.datetime = datetime;
            } catch (ParseException e) {
                throw new InvalidDateException("Ex: dd/MM/yyyy");
            }
        } else {
            throw new InvalidDateException("Ex: dd/MM/yyyy");
        }
    }

    private boolean dateTimeIsValid(String datetime) {
        try {
            Date date = DATE_FORMAT.parse(datetime);
            return datetime.equals(DATE_FORMAT.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

}
