package com.example.project_client.event;

import com.example.project_client.model.User;
import lombok.Getter;
import lombok.Setter;

public class Data {
    @Getter
    @Setter
    private static User user = new User();
    @Getter
    private static final String billPath = "D:\\Bill" ;

}
