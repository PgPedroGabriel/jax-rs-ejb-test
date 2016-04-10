package Entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Location.class)
public abstract class Location_ {

	public static volatile SingularAttribute<Location, String> address;
	public static volatile CollectionAttribute<Location, Event> eventCollection;
	public static volatile SingularAttribute<Location, String> name;
	public static volatile SingularAttribute<Location, Long> id;

}

