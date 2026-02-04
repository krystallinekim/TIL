package com.beyond.set.practice;

import java.util.Objects;

public class Music implements Comparable<Music> {
    private String title;
    private String artist;
    private int ranking;

    public Music(String title, String artist, int ranking) {
        this.title = title;
        this.artist = artist;
        this.ranking = ranking;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getRanking() {
        return ranking;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", ranking=" + ranking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return ranking == music.ranking && Objects.equals(title, music.title) && Objects.equals(artist, music.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, ranking);
    }

    @Override
    public int compareTo(Music music) {
        return this.ranking - music.ranking;
    }

}
