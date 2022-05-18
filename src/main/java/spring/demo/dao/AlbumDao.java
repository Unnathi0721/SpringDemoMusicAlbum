package spring.demo.dao;

import spring.demo.entity.Album;
import spring.demo.entity.Artist;

import java.util.List;


public interface AlbumDao {
    public void addAlbum(Album album);
    public void delete(int id);
    public Album getAlbum(int id);
    public Artist getArtist(int id);
    public List<Album> findAll();
    public List<Album> searchBy(String theName);
}
