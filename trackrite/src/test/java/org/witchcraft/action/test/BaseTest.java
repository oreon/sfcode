package  org.witchcraft.action.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.Search;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.AfterClass;
import org.witchcraft.base.entity.BusinessEntity;
import org.witchcraft.seam.action.BaseAction;

public abstract class BaseTest<T extends BusinessEntity> extends SeamTest{
	
	private static final String NOMBRE_PERSISTENCE_UNIT = "appEntityManager";
	private EntityManagerFactory emf;
	protected EntityManager em;

	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	protected EntityManager getEntityManager(){
		return em;
	}

	
	public void init() {
		emf = Persistence.createEntityManagerFactory(NOMBRE_PERSISTENCE_UNIT);
		em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		//getAction().setEntityManager(Search.getFullTextEntityManager(em));
	}
	
	 public BaseAction<T> getAction(){
		 return null;
	 }

	@AfterClass
	public void destroy() {
	//	em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
