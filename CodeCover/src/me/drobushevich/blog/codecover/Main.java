package me.drobushevich.blog.codecover;

import java.util.ArrayList;
import java.util.List;

import me.drobushevich.blog.codecover.out.Hello;
import me.drobushevich.blog.codecover.out.Speaker;

public class Main {

    public static void main(String[] args) {
        new Main().speak();
    }

    private List<Speaker> speakers;

    public Main() {
        speakers = new ArrayList<Speaker>();
        speakers.add(new Hello());
    }

    public void speak() {
        for (Speaker speaker : speakers) {
            speaker.say();
        }
    }
}
