package ru.suleimenov.Habr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
    @Value("0")
    @Column(name = "likes")
    private int likeCount;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Like> likes;

    private String string;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Favorites> favorites;
    @Value("0")
    @Column(name = "favorites")
    private int favoritesCount;




    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void addTag(Tag tag1){
        this.tag.add(tag1);
    }

    public void addLike(boolean isLiked){
        if (isLiked == true){
            likeCount--;
        }else likeCount++;
    }

    public void addFavorites(boolean isFavorites){
        if (isFavorites == true){
            favoritesCount--;
        }else favoritesCount++;
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
