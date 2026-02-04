package com.beyond.list.practice;

import java.util.Comparator;

public class ArtistAscending implements Comparator<Music> {

    // compare()을 오버라이드
    @Override
    public int compare(Music music1, Music music2) {
        return music1.getArtist().compareTo(music2.getArtist());
    }
}
