# Cadastro de veículos
[![NPM](https://img.shields.io/npm/l/express)](https://github.com/brunocesaromax/cadastro-veiculos/blob/main/LICENSE)

# Sobre o projeto

https://cadastro-veiculos-angular.herokuapp.com/

Cadastro de veículos é uma aplicação full stack web que consiste em um CRUD simples para veículos. O projeto foi construído durante a fase de implementação de um processo seletivo que participei.

## Layout Web

![Img 1](https://github.com/brunocesaromax/cadastro-veiculos/blob/main/front-web/cadastro-veiculos-front/src/assets/cv-img1.png)
![Img 2](https://github.com/brunocesaromax/cadastro-veiculos/blob/main/front-web/cadastro-veiculos-front/src/assets/cv-img2.png)

## Modelo Conceitual

![Modelo Conceitual](https://github.com/brunocesaromax/cadastro-veiculos/blob/main/backend/src/main/resources/static/images/diagram-class.png)

## Modelo de banco de dados

![Modelo bd](https://github.com/brunocesaromax/cadastro-veiculos/blob/main/backend/src/main/resources/static/images/entity-diagram.png)

## Tecnologias utilizadas

### Back end
- Java 11
- Spring Boot 2.5.0
- JPA/ Hibernate
- H2 [test] (database: testdb | username: sa)
- PostgreSql (database: cadastro-veiculos | username: postgres  | password: 1234567)
- Maven

### Front end
- HTML / SASS / JS / Typescript
- Angular 11
- PrimeNG 11

## Como executar o projeto

### Back end

Pré-requisitos: Java 11

  1 - Clonar o repositório: 
  
  ```bash 
  git clone https://github.com/brunocesaromax/cadastro-veiculos.git
  ```
  
  
  2 - Entrar na pasta do projeto back end: 
  
  ```bash 
  cd backend
  ```
  
  3 - Executar o projeto:
  
  
  ```bash 
  ./mvnw spring-boot:run
  ```

### Front end web

Pré-requisitos: node >= 10.19.0, npm >= 6.14.11

  1 - Clonar o repositório: 
  
  ```bash 
  git clone https://github.com/brunocesaromax/cadastro-veiculos.git
  ```
  
  
  2 - Entrar na pasta do projeto front end: 
  
  ```bash 
  cd front-web/cadastro-veiculos-front
  ```
  
  3 - Instalar dependências
  
  ```bash 
  npm install
  ```
  
  4 - Executar o projeto:
  
  
  ```bash
  ng serve
  ```
  
# Autor

Bruno César Vicente

 <a href="https://www.linkedin.com/in/bruno-cesar-vicente" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a> 
