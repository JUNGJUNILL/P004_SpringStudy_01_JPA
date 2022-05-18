package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype") //clientGubun 비슷한 역할(?)
@Getter
@Setter
public abstract class Item {


    @Id
    @GeneratedValue
    @Column(name = "Item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
