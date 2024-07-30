package jpabook.jpashop.domain.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of={"id","username","age"})
public class Member_practice {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="team_id")
private Team_practice team;

    public Member_practice(String username){
        this(username,0);

    }
    public Member_practice(String username,int age){
        this(username,age,null);
    }
public Member_practice(String username,int age,Team_practice team){
    this.username=username;
    this.age=age;
    if(team !=null){
        changeTeam(team);
    }
}

    private void changeTeam(Team_practice team) {
    this.team=team;
    team.getMembers().add(this);
    }
}
