docker run ^
  --name kh-mysql-container ^
  -v ./data:/var/lib/mysql ^
  -v ./schema:/docker-entrypoint-initdb.d ^
  -p 3306:3306 ^
  -e MYSQL_DATABASE=kh ^
  -e MYSQL_ROOT_PASSWORD=notroot ^
  -d mysql:latest ^
