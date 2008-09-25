How to run the testcase:

1. change the content in configuration.properties to configure the xml files path.
2. change the content in config.properties to configure the wsdl path.
3. configure the entity manager in getEntityManager in AccuracyBaseTest.java file, also you can use the following configuration:, just to create EntityManager correctly.

        if (manager == null || !manager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure("config_for_ear/hibernate.cfg.xml");

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            manager = emf.createEntityManager();
        }