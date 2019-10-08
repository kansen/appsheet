package com.appsheet.sample.model;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Kansen Wu
 */
public class User {
    private int id;
    private String name;
    private int age;
    private String number;
    private String photo;
    private String bio;

    public User (int id, String name, int age, String number, String photo, String bio){
        this.id = id;
        this.name = name;
        this.age = age;
        this.number = number;
        this.photo = photo;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Comparator to sort user list in the order of Age
     */
    public static Comparator<User> AgeComparator = new Comparator<User>() {

        @Override
        public int compare(User u1, User u2) {
            return u1.getAge() - u2.getAge();
        }
    };

    /**
     * Comparator to sort user list or array in the order of Name
     */
    public static Comparator<User> NameComparator = new Comparator<User>() {

        @Override
        public int compare(User u1, User u2) {
            return u1.getName().compareTo(u2.getName());
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name) &&
                age == user.age &&
                photo.equals(user.photo) &&
                bio.equals(user.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User: {" +"id=" + id +", name='" + name +", age=" + age + ", photo=" + photo +
                ", number=" + number + ", bio=" + bio + "}";
    }
}

