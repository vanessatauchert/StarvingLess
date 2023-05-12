//package com.fatec.starvingless.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fatec.starvingless.entities.Comment;
//import com.fatec.starvingless.entities.User;
//import com.fatec.starvingless.services.exceptions.InvalidDateException;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//import java.io.Serial;
//import java.io.Serializable;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class CommentDTO implements Serializable {
//
//    @Serial
//    private static final long serialVersionUID = 1L;
//
//    private Long id;
//    @javax.validation.constraints.Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-!,]*$",
//            message = "Description cannot contain special characters")
//    @Size(max = 250, message = "Description must be between 10 and 250 chars")
//    @NotBlank
//    private String description;
//    @NotBlank(message = "Required field")
//    private String createDate;
//
//    private Long postId;
//
//    private Long userId;
//
//    private String firstName;
//
//    public CommentDTO(Comment entity, String firstName) {
//        id = entity.getId();
//        description = entity.getDescription();
//        createDate = entity.getCreateDate();
//        postId = entity.getPost() != null ? entity.getPost().getId() : null;
//        this.userId = entity.getId();
//        this.firstName = firstName;
//
//    }
//
//    public CommentDTO(User user){
//        firstName = user.getFirstName();
//    }
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
//
//    public void setCreateDate(String createDate) {
//        if (createDateIsValid(createDate)) {
//            try {
//                Date date = DATE_FORMAT.parse(createDate);
//                Date currentDate = new Date();
//                if (date.after(currentDate)) {
//                    throw new InvalidDateException("SignUpDate cannot be greater than the current date");
//                }
//                this.createDate = createDate;
//            } catch (ParseException e) {
//                throw new InvalidDateException("Ex: dd/MM/yyyy");
//            }
//        } else {
//            throw new InvalidDateException("Ex: dd/MM/yyyy");
//        }
//    }
//
//    private boolean createDateIsValid(String createDate) {
//        try {
//            Date date = DATE_FORMAT.parse(createDate);
//            return createDate.equals(DATE_FORMAT.format(date));
//        } catch (ParseException e) {
//            return false;
//        }
//    }
//
//}
