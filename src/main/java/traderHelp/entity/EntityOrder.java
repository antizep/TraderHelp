package traderHelp.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EntityOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(unique = true)
	private int count;
	private float price;
	private float summ;
	private boolean type;
	private Date date;
	private boolean closed;
	
	@Override
	public String toString() {
		return "EntityOrder [id=" + id + ", name=" + name + ", count=" + count + ", price=" + price + ", summ=" + summ
				+ ", type=" + type + ", date = "+ date+"]";
	}
	

}
