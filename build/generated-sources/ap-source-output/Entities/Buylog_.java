package Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Buylog.class)
public abstract class Buylog_ {

	public static volatile SingularAttribute<Buylog, Event> eventId;
	public static volatile SingularAttribute<Buylog, Integer> quantity;
	public static volatile SingularAttribute<Buylog, Double> price;
	public static volatile SingularAttribute<Buylog, Buy> buyId;
	public static volatile SingularAttribute<Buylog, Long> id;

}

