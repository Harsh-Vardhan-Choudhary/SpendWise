package com.harsh.SpendWise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//import lombok.Getter;
//import lombok.Setter;

//for jpa we need to have no argument constructor
@NoArgsConstructor          
@Entity
@Data       //@Data - lombok make sure it create every thing such as getters, setters, toString, etc                     
@Table(name = "appuser")
public class User {
    
    //primary key
    @Id     
    private Long id;

    //since we are using lombok we can create getter and setter by such notation
    //@Getter @Setter
    private String name; 

    private String email;

}
