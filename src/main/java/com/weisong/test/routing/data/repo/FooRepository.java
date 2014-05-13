package com.weisong.test.routing.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weisong.test.routing.data.model.Foo;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
