package me.snowlight.codingtestspringdaomybatis.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.snowlight.codingtestspringdaomybatis.model.SearchKeyword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqMember {
    private String name;
    private Integer age;

    public SearchKeyword toCommand() {
        return new SearchKeyword(this.name, this.age);
    }
}
