package com.example.movies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String id;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("nickname")
    private String nickname;

    //email
    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;
}
