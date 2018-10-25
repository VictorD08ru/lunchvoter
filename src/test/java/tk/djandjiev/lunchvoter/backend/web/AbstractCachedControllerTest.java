package tk.djandjiev.lunchvoter.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import tk.djandjiev.lunchvoter.backend.util.JpaUtil;

public abstract class AbstractCachedControllerTest extends AbstractControllerTest {

    @Autowired
    protected CacheManager cacheManager;

    @Autowired(required = false)
    protected JpaUtil jpaUtil;

    void setUp() {
        if (jpaUtil == null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }
}
