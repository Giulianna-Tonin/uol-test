package br.com.cadastro.api;

public class UserDTO {

    private Long id;
    private String name;
    private String age;
    private Double postDayMaxTemp;
    private Double postDayMinTemp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Double getPostDayMaxTemp() {
        return postDayMaxTemp;
    }

    public void setPostDayMaxTemp(Double postDayMaxTemp) {
        this.postDayMaxTemp = postDayMaxTemp;
    }

    public Double getPostDayMinTemp() {
        return postDayMinTemp;
    }

    public void setPostDayMinTemp(Double postDayMinTemp) {
        this.postDayMinTemp = postDayMinTemp;
    }
}

