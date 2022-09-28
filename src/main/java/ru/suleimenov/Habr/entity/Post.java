package ru.suleimenov.Habr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tag;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String text;
    private LocalDate date;
    private LocalTime time;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Comment> commentList;
    private String string;




    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void addTag(Tag tag1){
        this.tag.add(tag1);
    }



    public String getFormatDate() {

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = this.date.format(myFormatObj);
        return formattedDate;
    }

    public String getHeader() {
        return header;
    }

    public String getFormatTime() {

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = this.time.format(myFormatObj);
        return formattedDate;
    }


    public Long getUserId() {
        return this.user.getId();
    }
    public void addComment(Comment comment){
        commentList.add(comment);
    }




}
