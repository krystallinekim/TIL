package com.beyond.list.practice;

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

    // 구체적인 타입 지정 안하면 Object로 고정
    // @Override
    // public int compareTo(Object o) {
    //     return 0;
    // }

    @Override
    public int compareTo(Music music) {
        // System.out.printf("this.ranking = %d, music.ranking = %d\n", this.ranking, music.ranking);
        // 오름차순
        return this.ranking - music.ranking;
    }

}
