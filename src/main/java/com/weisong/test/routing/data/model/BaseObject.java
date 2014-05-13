package com.weisong.test.routing.data.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
