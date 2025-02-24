## Vacancy Web application
## Installation guide

## Overview
Vacature Web is een Java spring-boot react-webapplicatie waarmee de
systeemeigenaar de vacature kan plaatsen en mensen kan laten solliciteren naar de functie.


 1. Zorg dat u beschikt over een computer die voldoet aan de minimale systeemvereisten voor Java, Node.js en React.

 2. Installeer de benodigde software en tools. Hieronder vindt u de lijst met software en tools die u nodig hebt voor de installatie van deze applicatie:
Java Development Kit (JDK)
Node.js
JetBrains IntelliJ IDEA (of een andere geschikte IDE)

 3. Installeer Node.js-modules. Voer het volgende commando uit in uw terminal of command prompt:
npm install

 4. Installeer React. Voer het volgende commando uit in uw terminal of command prompt:
npm install -g react

 5. Kloon de broncode van de applicatie vanuit uw beheerde versiebeheersysteem (bijvoorbeeld GitHub) naar uw lokale systeem.

 6. Importeer het project in JetBrains IntelliJ IDEA en configureer eventuele projectinstellingen.

 7. Voer de benodigde afhankelijkheden uit via Maven. Voer het volgende commando uit in uw terminal of command prompt:
mvn install

Let op: deze stappen zijn algemene richtlijnen en kunnen afhankelijk zijn van uw specifieke installatiesituatie. Het is aan te raden om de documentatie en hulpbronnen van de gebruikte software en tools te raadplegen voor verdere ondersteuning.

## Installatiehandleiding:
1.1.	 Setup postgresdb

Controleer application.properties voor verbindingsdetails
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/les17
spring.datasource.username=postgres
spring.datasource.password=novibootcamp
spring.datasource.driver-class-name=org.postgresql.Driver

1.2.	Data: controleer data.sql
1.3.	Build command

mvn clean install

1.4.	Run command

mvn spring-boot run

Om te testen: http://localhost:8080



## 1. Installatie stappen

2. Setup posgresdb
Zie application.properties voor connectie detail

spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/les17
spring.datasource.username={UWGEBRUIKERSNAAM}
spring.datasource.password={UWWACHTWOORD}
spring.datasource.driver-class-name=org.postgresql.Driver

## 3. zie data: check data.sql
admin: gebruikersnaam: dd0gan /  password: appel
user: karel / password: appel  



test: http://localhost:8080


