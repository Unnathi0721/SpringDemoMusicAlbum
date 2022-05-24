package spring.demo.service;

import spring.demo.entity.Album;
import spring.demo.entity.Artist;

import java.util.List;

public interface ArtistService {
    public void addArtist(Artist artist);
    public void delete(int id);
    public Artist getArtist(int id);
//    public List<Album> getAlbums(int id);
    public List<Artist> findAll();
    public List<Artist> searchBy(String theName);
}
