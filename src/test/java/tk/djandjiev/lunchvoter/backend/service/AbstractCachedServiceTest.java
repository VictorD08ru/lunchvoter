package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import tk.djandjiev.lunchvoter.backend.util.JpaUtil;

public abstract class AbstractCachedServiceTest extends AbstractServiceTest {

    @Autowired
    protected CacheManager cacheManager;

    @Autowired(required = false)
    protected JpaUtil jpaUtil;

    void setUp() throws Exception {
        if (jpaUtil == null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }
}
