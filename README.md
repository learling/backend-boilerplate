# springboot-backend

## git-ignored
```git rm --cached src/main/resources/application.properties```

**WARNING**: Following values are not for production mode!
```properties
spring.datasource.url = jdbc:mysql://xxx.xxx.xxx/xxx?useSSL=false
spring.datasource.username = xxx
spring.datasource.password = xxx

# populate using src/main/resources/schema.sql (or data.sql - whatever)
# always = not only embedded databases 
spring.sql.init.mode = always
spring.jpa.hibernate.ddl-auto = none
# Log each sql query
spring.jpa.show-sql = true

# https
server.port = 8443
server.ssl.enabled = true
server.ssl.key-store = classpath:xxx.p12
server.ssl.key-store-password = xxx
server.ssl.key-password = xxx
server.ssl.key-store-type = PKCS12
server.ssl.key-alias = xalias

# API-KEY: XXX
springbootbackend.http.auth-token-header-name = API-KEY
springbootbackend.http.auth-token = XXX

# email
mail.from = xxxxx@xxxxx.xxx

spring.mail.host = xxxx.xxxxxx.xxx
spring.mail.port = 587
spring.mail.username = xxxxx
spring.mail.password = SG.xXxX

spring.mail.properties.mail.smtp.auth = true
spring.mail.properties.mail.smtp.connectiontimeout = 5000
spring.mail.properties.mail.smtp.timeout = 5000
spring.mail.properties.mail.smtp.writetimeout = 5000

spring.mail.properties.mail.smtp.starttls.enable = true
```

---

## AWS
First make sure your domain-name points to the public address of the new Ubuntu server.

In the local directory where you downloaded the amazon key-file, ssh to your fresh Ubuntu EC2 instance:
```console
ivanne@desktop:~/Documents/AWS$ chmod 400 key.pem
ivanne@desktop:~/Documents/AWS$ ssh -i "key.pem" ubuntu@ec2-3-248-220-0.eu-west-1.compute.amazonaws.com
```
Once connected, install the free certbot from Let's Encrypt, generate a certificate, convert it to PKCS12 and prepare the copy:
```console
sudo apt update
sudo apt install -y certbot
sudo certbot certonly -a standalone -d test.ivanne.api
sudo su
cd /etc/letsencrypt/live/test.ivanne.api
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem \
 -out springboot_letsencrypt.p12 -name bootalias -CAfile chain.pem -caname root

cp springboot_letsencrypt.p12 /home/ubuntu
exit
cd
ls
sudo chmod 777 springboot_letsencrypt.p12
exit
```
Secure copy the key to the Spring Boot ```classpath:```
```console
ivanne@desktop:~/Documents/AWS$ sudo scp -i "key.pem" ubuntu@ec2-3-248-220-0.eu-west-1.compute.amazonaws.com:/home/ubuntu/springboot_letsencrypt.p12 .
ivanne@desktop:~/Documents/AWS$ cp springboot_letsencrypt.p12 ~/Projects/learling/fullstack-boilerplate/backend-boilerplate/src/main/resources/
```
In your IntelliJ Terminal, create a docker image and test it (you may ```rm -f``` the container before):
```console
./mvnw clean package
sudo docker build -t <privatehub>/springboot-backend .
sudo docker run --name springboot-backend -dp 443:8443 <privatehub>/springboot-backend
sudo docker stop springboot-backend
```
Check if your repo is public or private and upload your image to the hub **(the syntax with the password as parameter is not secure though)**:
```console
sudo docker login -u <privateuser> [-p <password>]
sudo docker push <privatehub>/springboot-backend
```
Connect to the AWS machine again:
```console
ivanne@desktop:~/Documents/AWS$ ssh -i "key.pem" ubuntu@ec2-3-248-220-0.eu-west-1.compute.amazonaws.com
```
As ubuntu user, install Docker, pull the image and run it:
```console
sudo apt update
sudo apt install -y docker.io
sudo docker login -u <privateuser> [-p <password>]
sudo docker pull <privatehub>/springboot-backend
sudo docker run --name springboot-backend -dp 443:8443 <privatehub>/springboot-backend
```

---

## Raspberry Pi

Install Docker on...
```console
uname -a
cat /etc/os-release
```
```Raspbian GNU/Linux 10 (buster)```:
```console
sudo apt update
sudo apt install docker.io
```
Check the installation:
```console
sudo docker version
sudo docker info
sudo docker run hello-world
```

### armv7l

Instead of ```FROM openjdk:11```, start the Dockerfile with ```FROM adoptopenjdk/openjdk11:armv7l-debianslim-jdk11u-nightly-slim```.
This base only works on armv7l.

The architecture of my Rapsberry Pi Zero is armv6l and I couldn't find any appropriate image in Docker Hub. 
Fortunately we have another Pi with armv7l, so we can still host the API at home.

### https

To be able to get the certificate, turn off the service listening on port 80:
```console
sudo service apache2 status
q
sudo service apache2 stop
```
Set up a port-forwarding for HTTP (80) and HTTPS (443) on your router and dynamic DNS with your domain hosting company.
The next steps are similar to the ones with AWS.

---

## docker-compose

Check if you have docker-compose installed 
(in my case I couldn't even uninstall it -
and I wonder why my build is unknown):

```console
ivanne@ivanne-desktop:~$ docker-compose --version
docker-compose version 1.25.5, build unknown
```

In the app root directory, create ```docker-compose.yml```:

```yaml
version: "3.7"
services:
    mysql_db:
        image: mysql:8.0
        ports:
            - 3306:3306
        environment:
            MYSQL_DATABASE: boilerplate
            MYSQL_USER: bp_user
            MYSQL_PASSWORD: <strongpassword>
            MYSQL_ROOT_PASSWORD: <strongpassword>
        container_name: mysql_db_container
    api_service:
        image: ivanne/springboot-backend
        ports:
            - 8080:8080
        depends_on:
            - mysql_db
        environment:
            SPRING_DATASOURCE_URL: jdbc:mysql://mysql_db:3306/boilerplate?useSSL=false&allowPublicKeyRetrieval=true
        container_name: api_service_container
```

Change the first 3 lines of ```src/main/resources/application.properties``` 
(in my case it only works with root):

```properties
spring.datasource.url = jdbc:mysql://mysql_db:3306/boilerplate?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username = root
spring.datasource.password = <strongpassword>
```

Clean up your docker-stuff if required.

Rebuild the API-service-image and pre-start docker-compose:
```console
./mvnw clean package -DskipTests=true
sudo docker build -t ivanne/springboot-backend .
sudo docker-compose up
^C
```
Go into the MySQL-server-image:
```console
sudo docker start mysql_db_container
sudo docker exec -it mysql_db_container bash
```
Login as root with your own ```<strongpassword>```;
```
mysql -u root -p
```
This is to allow the ```TRIGGER```-statements:
```sql
set global log_bin_trust_function_creators=1;
```
Configure the non-root user (but I still get ```Access denied```):
```sql
CREATE USER 'bp_user'@'localhost' IDENTIFIED BY '<strongpassword>';
FLUSH PRIVILEGES;
GRANT SELECT ON * . * TO 'bp_user'@'localhost';
CREATE DATABASE `boilerplate`;
GRANT ALL PRIVILEGES ON `boilerplate` . * TO 'bp_user'@'localhost';
FLUSH PRIVILEGES;
```
Rerun both linked containers in detached (or daemon) mode.
```console
sudo docker-compose --verbose up -d
```

I tried it on my local 20.04.1-Ubuntu. 
On my Raspbian system it won't work due to lack of disk space.

## Swagger

/swagger-ui/index.html