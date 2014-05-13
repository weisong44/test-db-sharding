package com.weisong.test.routing.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.weisong.test.common.ContextHolder;
import com.weisong.test.routing.data.model.Foo;
import com.weisong.test.routing.data.repo.FooRepository;
import com.weisong.test.routing.data.sharding.ShardRegistry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-context.xml")
//@Transactional
public class FooRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(FooRepositoryTest.class);

    @Value("${db.host}") private String dbHost;
    @Value("${db.name}") private String dbName;
    @Value("${db.username}") private String dbUsername;
    @Value("${db.password}") private String dbPassword;
    
    @Autowired private ShardRegistry registry;
    @Autowired private FooRepository fooRepo;
    
    final static public String SCHEMA1 = "aaa";
    final static public String SCHEMA2 = "bbb";
    
    @Before
    public void setUp() {
    	registerShard(1L, SCHEMA1);
    	registerShard(2L, SCHEMA2);
    }
    
    private void registerShard(Long shardId, String schema) {
    	registry.addShard(shardId, dbHost, dbName, schema, dbUsername, dbPassword);
    }
    
    @Test
    public void test() {
        try {
        	{
            	ContextHolder.get().setShardId(1L);
                Foo foo = new Foo(1, "foo");
                fooRepo.saveAndFlush(foo);
        	} {
            	ContextHolder.get().setShardId(2L);
                Foo foo = new Foo(2, "foo");
                fooRepo.saveAndFlush(foo);
        	}
        } catch (Exception e) {
            log.error("Error saving Foo.", e);
            throw e;
        }
    }

}
