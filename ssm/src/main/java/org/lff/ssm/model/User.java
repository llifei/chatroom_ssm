package org.lff.ssm.model;

import java.util.List;

public class User {
    private Integer id;
    private String username;
    private String password;
    private List<Integer> friendIds;
    private List<String> friendNames;

    public List<String> getFriendNames() {
        return friendNames;
    }

    public void setFriendNames(List<String> friendNames) {
        this.friendNames = friendNames;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", friendIds=" + friendIds +
                ", friendNames=" + friendNames +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Integer> friendIds) {
        this.friendIds = friendIds;
    }

    public User(Integer id, String username, String password, List<Integer> friendIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.friendIds = friendIds;
    }

    public void addFriend(Integer id, String name) {
        this.friendIds.add(id);
        this.friendNames.add(name);
    }

    public void delFriend(Integer id, String name){
        if(this.friendIds != null && this.friendIds.contains(id))
            this.friendIds.remove(this.friendIds.indexOf(id));
        if(this.friendNames != null && this.friendNames.contains(name))
            this.friendNames.remove(name);
    }
}
