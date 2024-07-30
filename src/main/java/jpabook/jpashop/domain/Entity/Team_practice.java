package jpabook.jpashop.domain.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","name"})
public class Team_practice {


@Id @GeneratedValue
@Column(name="team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    List<Member_practice> members=new ArrayList<>();


    public Team_practice(String name){
        this.name=name;
    }
}
