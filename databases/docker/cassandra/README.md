* After executing the docker-compose file (about 2 minutes) and ingressing it to `localhost:3000` in your browser.
* Create a keyspace to contain all the tables:
  ```sql
   CREATE KEYSPACE twa WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'} AND durable_writes = true;
  ```
* Create a table  
  ```sql
   CREATE TABLE twa.country (
          code text,
          id uuid,
          currency frozen <currency>,
          enabled boolean,
          locale text,
          name text,
          states list<frozen<twa.state {id bigint, code text, name text, enabled boolean, countryid text}>>,
          timezone text,
          PRIMARY KEY (code, id)
        )
        WITH CLUSTERING ORDER BY (id DESC)
          AND bloom_filter_fp_chance = 0.01
          AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
          AND comment = ''
          AND compaction = {'class': 'SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
          AND compression = {'chunk_length_in_kb': '16', 'class': 'LZ4Compressor'}
          AND crc_check_chance = 1.0
          AND dclocal_read_repair_chance = 0.0
          AND default_time_to_live = 0
          AND gc_grace_seconds = 864000
          AND max_index_interval = 2048
          AND memtable_flush_period_in_ms = 0
          AND min_index_interval = 128
          AND read_repair_chance = 0.0
          AND speculative_retry = '99p';
  ```
* Test: http://localhost:8080/api/catalog/country/1
* Test: http://localhost:8080/api/catalog/country/1