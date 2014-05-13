test-db-sharding
================
This is a test project for database sharding based on Postgres, it has the following properties:
 - Shards can be spread across database and schema, each schema is a shard and a database can contain multiple schemas.
 - Every shard is assigned with an Id (shardId), internally shardId is mapped to host+database+schema
 - Shards can be added dynamically at runtime, schema must be initialized prior to addition
 - Every model object contains a shardId, which determines its placement in database system, this is not needed by the sharding system, however required by correlation in other places
 - A context (ThreadLocal) containing the current shardId must be setup prior to any database operations 
 - Internally, a special datasource containing concrete datasources is used to provide routing behavior
 - The real datasource is determined when a transaction is started, and no more switch will happen during the transaction. This goes unfortunately for both operations across shema and database. This is fundamentally a limitation of Spring JTA implementation. To overcome this, additional design will be required at service layer.
 - Adequete checks have be built in, in order to avoid objects with wrong shardId being stored in wrong shard
 - Specific to Hibernate, a default shard must be setup in one of the databases (schema: public), this is for initialization reasons

TODO
====
 - Add better shard management
 - Add a service and RESTful layer
 - Add automatic schema initialization mechanism
