package spring.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artist")
public class Artist {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotNull(message="is required")
    @Size(min=1, message="size should be minimum of length 1")
    @NotBlank(message="should not be empty")
    @Column(name="artist")
    private String artist;
    public Artist(){

    }
    public Artist(String artist){
        this.artist=artist;
    }
//    @OneToMany(mappedBy="artist",
//            cascade= CascadeType.ALL)
    @OneToMany(mappedBy="artist",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})

    private List<Album> albums;
    public Artist(String artist, List<Album> albums) {
        this.artist = artist;
        this.albums = albums;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void add(Album temp) {

        if (albums == null) {
            albums = new ArrayList<>();
        }

        albums.add(temp);

        temp.setArtist(this);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                '}';
    }
}
