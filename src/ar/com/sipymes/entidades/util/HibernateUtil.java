package ar.com.sipymes.entidades.util;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

	private static final SessionFactory factory;

	static {
		try {
			// TODO: sacar el buildSessionFactory deprecado
			// Creacion del Seesion Factory
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo la creación del SessionFactory: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Crea una nueva session.
	 * @return Session nueva
	 */
	public static final Session getSession() {
		return factory.openSession();
	}

	/**
	 * Limpio la cache de la session
	 */
	public static final void clearCache() {
		Cache cache = factory.getCache();
		
		if (cache != null) {
			cache.evictAllRegions();
			cache.evictEntityRegions();
			cache.evictCollectionRegions();
			cache.evictDefaultQueryRegion();
			cache.evictQueryRegions();
		}
	}

	
}
