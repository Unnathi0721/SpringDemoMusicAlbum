package spring.demo.entity;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.service.ArtistService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="album")
public class Album {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotNull(message="is required")
    @Size(min=1, message="is required")
    @Column(name="title")
    private String title;
    @NotNull(message="is required")
    @NotBlank(message="should not be empty")
    @Column(name="language")
    private String language;
    @NotNull(message="is required")
    @Range(min=1, message="stock value should be more than zero")
    @Column(name="stock")
    private int stock;
    @ManyToOne(cascade= { CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="artist_id")
    private Artist artist;
    public Album(){

    }

    public Album(String title, String language, int stock,Artist artist) {
        this.title = title;
        this.language = language;
        this.stock=stock;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", stock=" + stock +
                ", artist=" + artist.toString() +
                '}';
    }
}
