test-db-sharding
================
This is a test project for database sharding based on Postgres, it has the following properties:
 - Shards can be spread across database and schema, each schema is a shard and a database can contain multiple schemas.
 - Every shard is assigned with an Id (shardId), internally shardId is mapped to host+database+schema
 - Shards can be added dynamically at runtime, schema must be initialized prior to addition
 - Every model object contains a shardId, which determines its placement in database system
 - A context containing the current shardId must be setup, before any database operations can be applied to an object
 - Internally, a special datasource containing concrete datasources is used to provide routing behavior
 - The real datasource is determined when a transaction is started, and no more switch will happen during the transaction
 - Adequete checks have be built in, in order to avoid objects with wrong shardId being stored in wrong shard
 - Specific to Hibernate, a default shard must be setup in one of the databases (schema: public), this is for initialization reasons
