package Entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Buy.class)
public abstract class Buy_ {

	public static volatile SingularAttribute<Buy, Date> dateCreated;
	public static volatile CollectionAttribute<Buy, Buylog> buylogCollection;
	public static volatile SingularAttribute<Buy, Long> id;
	public static volatile SingularAttribute<Buy, User> userId;

}

