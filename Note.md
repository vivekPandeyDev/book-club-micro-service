Apply spring java format
```shell
./mvnw spring-javaformat:apply 
```
To fix javadoc
```shell
./mvnw javadoc:fix
```
Generate Java jar with java doc
```shell
./mvnw clean install javadoc:javadoc -DskipTests
```

## Go to spring boot project and build the docker image
```bash
.\mvnw clean install -DskipTests jib:build -X
```

Redis connect and monitor - run inside docker container
```shell
redis-cli -h redis -p 6379 -a password
# monitor command
monitor 
```

Caching usage
```text
@CachePut(value = "clubs", key = "#result.clubId")
@Cacheable(value = "clubs", key = "#id")
@CacheEvict(value = "clubs", key = "#id")

private final CacheManager cacheManager;
var cache = cacheManager.getCache("allBookClubs");
cache.get(key, PageDto.class)
```

Sql query
```postgres-sql
-- drop all table
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS public.' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

-- clean all table data
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'TRUNCATE TABLE public.' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

-- view current tables
select * from "public".book;
select * from "public".book_buy_link;
select * from "public".book_club;
select * from "public".book_tag;
select * from "public".book_genre;
select * from "public".review;

```

Rabbit mq binding
```java

    /**
     * Binding book queue to exchange(topic) with routing type book.updated 
     * It matches book.created exactly
     * @return Binding
     */
 	@Bean
	public Binding bookCreatedBinding() {
		return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(rabbitMQ.getCreateRoutingKey());
	}
    /**
     * binding book queue to exchange(topic) with routing type book.updated 
     * It matches book.updated exactly
     * @return Binding
     */
    @Bean
	public Binding bookUpdatedBinding() {
		return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(rabbitMQ.getUpdateRoutingKey());
	}

    /**
     * binding book queue to exchange(topic) with routing type book.*
     * It matches book.created, book.updated any one word matches
     * It doesn't match book, book.created.at means exact one match
     * @return Binding
     */
	@Bean
	public Binding bookBinding() {
		return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(rabbitMQ.getRoutingKey());
	}
    @Bean
    public TopicExchange bookExchange() {
        return new TopicExchange(rabbitMQ.getBookExchange());
    }
    
```

Restore Database

```postgres-psql
-- Step 0: move backup folder to docker
pg_dump -U postgres -d "book-db" > backup/backup_book_backup.sql;
-- Step 1: where -U username -d database name
psql -U postgres -d "book-db" < backup/backup_public.sql;
```

Restore Database to another schema
```postgres-psql
-- Step 0: Backup data
-- Create new data-base
psql -U postgres;
CREATE DATABASE book_backup_db;
\l
\q
-- dump database to backup file ( since main book-db contains all data to be back up)
pg_dump -U postgres -d "book-db" > backup/backup_book_backup.sql; 
sed -i 's/public./book_backup./g' backup/backup_book_backup.sql;
-- Step 1: Create new schema : book_backup
psql -U postgres -d "book_backup_db"; 
create schema book_backup;
\q
-- Step 2: restore command
psql -U postgres -d "book_backup_db" < backup/backup_book_backup.sql;
```

# How to create app network in docker
```shell
docker network create --driver bridge app-net
```

# Check merged branch
```shell
git checkout main
git pull origin main  # Optional: ensure you have the latest
git branch --merged
------------------
-- Get detail graph view
git log --graph --oneline --all --decorate
git log --merges --oneline main

```
# Delete branch after checking merged with main
```shell
 git branch -d feature/club-service
 git push origin --delete feature/club-service
 ----------------------
 git branch -d feature/book-service
 git push origin --delete feature/book-service

```

# Create a build package make sure java version 21 installed
```shell
mvnw clean spring-javaformat:apply spring-boot:build-image
mvnw -X compile spring-javaformat:apply jib:dockerBuild
```

# Push image to docker hub
```shell
mvnw clean install spring-javaformat:apply jib:build 
docker-compose -f docker-compose-app-remote.yml up --force-recreate --pull always
```
