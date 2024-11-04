docker run --name kh-mysql-container ^
  --mount type=bind,source=%cd%/data,target=/var/lib/mysql ^
  --mount type=bind,source=%cd%/schema,target=/docker-entrypoint-initdb.d ^
  -p 3306:3306 ^
  -e MYSQL_DATABASE=kh ^
  -e MYSQL_ROOT_PASSWORD=notroot ^
  -d mysql:latest ^
